package com.checkus.uvcdemo

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.checkus.uvcdemo.databinding.ActivityMainBinding
import com.jiangdg.ausbc.MultiCameraClient
import com.jiangdg.ausbc.base.CameraActivity
import com.jiangdg.ausbc.callback.ICameraStateCallBack
import com.jiangdg.ausbc.camera.bean.CameraRequest
import com.jiangdg.ausbc.widget.AspectRatioTextureView
import com.jiangdg.ausbc.widget.IAspectRatio
import com.suprema_ai.qfm_sdk_android.QFM_SDK_ANDROID
import com.suprema_ai.qfm_sdk_android.UsbService
import com.suprema_ai.qfm_sdk_android.enumeration.QF_ENROLL_OPTION
import com.suprema_ai.qfm_sdk_android.enumeration.QF_RET_CODE
import com.suprema_ai.qfm_sdk_android.message_handler.MessageHandler
import kotlin.concurrent.thread

/**
 * Q-Face Pro Demo Application
 *
 * This activity demonstrates:
 * 1. UVC camera stream for visible preview (via AndroidUSBCamera/libausbc)
 * 2. IR camera face recognition (via Q-Face SDK over USB serial)
 *
 * Q-Face Pro has two cameras:
 * - UVC camera: For visible preview (class 0xEF - Video class)
 * - IR camera: For face recognition via USB serial (class != 0xEF)
 */
class MainActivity : CameraActivity() {

    private lateinit var binding: ActivityMainBinding

    // Q-Face SDK components
    private var qfaceSdk: QFM_SDK_ANDROID? = null
    private var messageHandler: MessageHandler? = null
    private var isIrConnected = false
    private var isIrInitialized = false

    // USB broadcast receiver for IR camera (serial communication)
    private val irUsbReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                UsbService.ACTION_USB_PERMISSION_GRANTED -> {
                    Log.d(TAG, "IR USB Permission Granted")
                    isIrConnected = true
                    updateIrStatus("IR Camera: Permission Granted")
                    // Initialize SDK after permission granted
                    initializeSdkCommunication()
                }
                UsbService.ACTION_USB_DISCONNECTED -> {
                    Log.d(TAG, "IR USB Disconnected")
                    isIrConnected = false
                    isIrInitialized = false
                    updateIrStatus("IR Camera: Disconnected")
                    updateButtonStates(false)
                }
                UsbService.ACTION_NO_USB -> {
                    Log.d(TAG, "No IR USB Device")
                    isIrConnected = false
                    updateIrStatus("IR Camera: No Device")
                }
                UsbService.ACTION_USB_NOT_SUPPORTED -> {
                    Log.d(TAG, "IR USB Not Supported")
                    isIrConnected = false
                    updateIrStatus("IR Camera: Not Supported")
                }
                UsbService.ACTION_USB_PERMISSION_NOT_GRANTED -> {
                    Log.d(TAG, "IR USB Permission Not Granted")
                    isIrConnected = false
                    updateIrStatus("IR Camera: Permission Denied")
                }
            }
        }
    }

    companion object {
        private const val TAG = "QFaceDemo"
        private const val PERMISSION_REQUEST_CODE = 1001
        private const val BAUDRATE = 115200
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: Q-Face Demo started")
        checkAndRequestPermissions()
        setupButtons()
        initializeQFaceSdk()
    }

    override fun onResume() {
        super.onResume()
        checkConnectedDevices()
        qfaceSdk?.resumeService()
    }

    override fun onPause() {
        super.onPause()
        try {
            qfaceSdk?.pauseService()
        } catch (e: Exception) {
            Log.e(TAG, "Error pausing SDK service", e)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            qfaceSdk?.QF_CloseCommPort()
        } catch (e: Exception) {
            Log.e(TAG, "Error closing SDK", e)
        }
    }

    private fun initializeQFaceSdk() {
        try {
            messageHandler = MessageHandler(this)
            qfaceSdk = QFM_SDK_ANDROID(this, messageHandler, irUsbReceiver)
            Log.d(TAG, "Q-Face SDK initialized")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to initialize Q-Face SDK", e)
            updateIrStatus("IR Camera: SDK Init Failed")
        }
    }

    private fun initializeSdkCommunication() {
        thread {
            try {
                // Wait a bit for USB service to stabilize
                Thread.sleep(500)

                // Initialize system parameters
                qfaceSdk?.QF_InitSysParameter()
                Log.d(TAG, "QF_InitSysParameter called")

                // Initialize communication port
                val result = qfaceSdk?.QF_InitCommPort(BAUDRATE, false)
                Log.d(TAG, "QF_InitCommPort result: $result")

                runOnUiThread {
                    if (result == QF_RET_CODE.QF_RET_SUCCESS) {
                        isIrInitialized = true
                        updateIrStatus("IR Camera: Connected (${BAUDRATE}bps)")
                        updateButtonStates(true)
                        showEnrolledCount()
                    } else {
                        updateIrStatus("IR Camera: Init Failed ($result)")
                        updateButtonStates(false)
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Failed to initialize SDK communication", e)
                runOnUiThread {
                    updateIrStatus("IR Camera: Error - ${e.message}")
                    updateButtonStates(false)
                }
            }
        }
    }

    private fun setupButtons() {
        binding.btnEnroll.setOnClickListener {
            if (!isIrInitialized) {
                Toast.makeText(this, "IR Camera not ready", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            enrollFace()
        }

        binding.btnIdentify.setOnClickListener {
            if (!isIrInitialized) {
                Toast.makeText(this, "IR Camera not ready", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            identifyFace()
        }

        binding.btnDeleteAll.setOnClickListener {
            if (!isIrInitialized) {
                Toast.makeText(this, "IR Camera not ready", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            deleteAllFaces()
        }
    }

    private fun enrollFace() {
        updateIrStatus("Enrolling... Look at the IR camera")
        updateButtonStates(false)

        thread {
            try {
                val enrollId = IntArray(1)
                val imageQuality = IntArray(1)

                // Use auto-generated ID for demo
                val result = qfaceSdk?.QF_Enroll(
                    0,  // userID 0 = auto-assign
                    QF_ENROLL_OPTION.QF_ENROLL_ADD_NEW,
                    enrollId,
                    imageQuality
                )

                runOnUiThread {
                    when (result) {
                        QF_RET_CODE.QF_RET_SUCCESS -> {
                            val msg = "Enrolled! ID: ${enrollId[0]}, Quality: ${imageQuality[0]}%"
                            Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
                            updateIrStatus("IR Camera: $msg")
                            showEnrolledCount()
                        }
                        QF_RET_CODE.QF_ERR_TIME_OUT -> {
                            Toast.makeText(this, "Timeout - Face not detected", Toast.LENGTH_SHORT).show()
                            updateIrStatus("IR Camera: Enroll timeout")
                        }
                        QF_RET_CODE.QF_ERR_FAKE_DETECTED -> {
                            Toast.makeText(this, "Fake face detected!", Toast.LENGTH_SHORT).show()
                            updateIrStatus("IR Camera: Fake detected")
                        }
                        else -> {
                            Toast.makeText(this, "Enroll failed: $result", Toast.LENGTH_SHORT).show()
                            updateIrStatus("IR Camera: Enroll failed - $result")
                        }
                    }
                    updateButtonStates(true)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Enroll error", e)
                runOnUiThread {
                    Toast.makeText(this, "Enroll error: ${e.message}", Toast.LENGTH_SHORT).show()
                    updateIrStatus("IR Camera: Error")
                    updateButtonStates(true)
                }
            }
        }
    }

    private fun identifyFace() {
        updateIrStatus("Identifying... Look at the IR camera")
        updateButtonStates(false)

        thread {
            try {
                val userId = IntArray(1)
                val subId = ByteArray(1)

                val result = qfaceSdk?.QF_Identify(userId, subId)

                runOnUiThread {
                    when (result) {
                        QF_RET_CODE.QF_RET_SUCCESS -> {
                            val msg = "Matched! User ID: ${userId[0]}"
                            Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
                            updateIrStatus("IR Camera: $msg")
                        }
                        QF_RET_CODE.QF_ERR_NOT_FOUND -> {
                            Toast.makeText(this, "No match found", Toast.LENGTH_SHORT).show()
                            updateIrStatus("IR Camera: No match")
                        }
                        QF_RET_CODE.QF_ERR_TIME_OUT -> {
                            Toast.makeText(this, "Timeout - Face not detected", Toast.LENGTH_SHORT).show()
                            updateIrStatus("IR Camera: Identify timeout")
                        }
                        QF_RET_CODE.QF_ERR_FAKE_DETECTED -> {
                            Toast.makeText(this, "Fake face detected!", Toast.LENGTH_SHORT).show()
                            updateIrStatus("IR Camera: Fake detected")
                        }
                        else -> {
                            Toast.makeText(this, "Identify failed: $result", Toast.LENGTH_SHORT).show()
                            updateIrStatus("IR Camera: Identify failed - $result")
                        }
                    }
                    updateButtonStates(true)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Identify error", e)
                runOnUiThread {
                    Toast.makeText(this, "Identify error: ${e.message}", Toast.LENGTH_SHORT).show()
                    updateIrStatus("IR Camera: Error")
                    updateButtonStates(true)
                }
            }
        }
    }

    private fun deleteAllFaces() {
        updateIrStatus("Deleting all faces...")
        updateButtonStates(false)

        thread {
            try {
                val result = qfaceSdk?.QF_DeleteAll()

                runOnUiThread {
                    if (result == QF_RET_CODE.QF_RET_SUCCESS) {
                        Toast.makeText(this, "All faces deleted", Toast.LENGTH_SHORT).show()
                        updateIrStatus("IR Camera: All deleted")
                        showEnrolledCount()
                    } else {
                        Toast.makeText(this, "Delete failed: $result", Toast.LENGTH_SHORT).show()
                        updateIrStatus("IR Camera: Delete failed - $result")
                    }
                    updateButtonStates(true)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Delete error", e)
                runOnUiThread {
                    Toast.makeText(this, "Delete error: ${e.message}", Toast.LENGTH_SHORT).show()
                    updateIrStatus("IR Camera: Error")
                    updateButtonStates(true)
                }
            }
        }
    }

    private fun showEnrolledCount() {
        thread {
            try {
                val numOfTemplate = IntArray(1)
                val result = qfaceSdk?.QF_GetNumOfTemplate(numOfTemplate)

                if (result == QF_RET_CODE.QF_RET_SUCCESS) {
                    runOnUiThread {
                        val count = numOfTemplate[0]
                        updateIrStatus("IR Camera: Ready (${count} enrolled)")
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Failed to get enrolled count", e)
            }
        }
    }

    private fun updateIrStatus(status: String) {
        runOnUiThread {
            binding.irStatusText.text = status
            binding.irStatusText.setTextColor(
                if (isIrInitialized) {
                    ContextCompat.getColor(this, android.R.color.holo_green_light)
                } else {
                    ContextCompat.getColor(this, android.R.color.holo_orange_light)
                }
            )
        }
    }

    private fun updateButtonStates(enabled: Boolean) {
        runOnUiThread {
            binding.btnEnroll.isEnabled = enabled
            binding.btnIdentify.isEnabled = enabled
            binding.btnDeleteAll.isEnabled = enabled
        }
    }

    // ============== UVC Camera Methods (from libausbc) ==============

    private fun checkConnectedDevices() {
        val devices = getDeviceList() ?: emptyList()
        Log.d(TAG, "Connected USB devices: ${devices.size}")
        devices.forEachIndexed { index, device ->
            Log.d(TAG, "Device $index: ${device.deviceName}, VID=${device.vendorId}, PID=${device.productId}, Class=${device.deviceClass}")
        }

        if (devices.isEmpty()) {
            binding.statusText.text = "No USB cameras detected"
        } else {
            binding.statusText.text = "Found ${devices.size} USB device(s)"
            // Request permission for UVC camera (class 0xEF)
            val uvcDevice = devices.find { it.deviceClass == 0xEF || it.deviceClass == 239 }
            if (uvcDevice != null && !isCameraOpened()) {
                Log.d(TAG, "Requesting UVC camera permission: ${uvcDevice.deviceName}")
                requestPermission(uvcDevice)
            }
        }
    }

    private fun checkAndRequestPermissions() {
        val permissionsNeeded = mutableListOf<String>()

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {
            permissionsNeeded.add(Manifest.permission.CAMERA)
        }

        if (permissionsNeeded.isNotEmpty()) {
            Log.d(TAG, "Requesting permissions: $permissionsNeeded")
            ActivityCompat.requestPermissions(
                this,
                permissionsNeeded.toTypedArray(),
                PERMISSION_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                Log.d(TAG, "All permissions granted")
                Toast.makeText(this, "Permissions granted", Toast.LENGTH_SHORT).show()
            } else {
                Log.w(TAG, "Some permissions denied")
                Toast.makeText(this, "Camera permission is required", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun getRootView(layoutInflater: LayoutInflater): View {
        binding = ActivityMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun getCameraView(): IAspectRatio {
        return AspectRatioTextureView(this)
    }

    override fun getCameraViewContainer(): ViewGroup {
        return binding.cameraContainer
    }

    override fun getCameraRequest(): CameraRequest {
        // Q-Face Pro UVC camera: 720x1280 (portrait mode)
        return CameraRequest.Builder()
            .setPreviewWidth(720)
            .setPreviewHeight(1280)
            .create()
    }

    override fun onCameraState(
        self: MultiCameraClient.ICamera,
        code: ICameraStateCallBack.State,
        msg: String?
    ) {
        when (code) {
            ICameraStateCallBack.State.OPENED -> {
                Log.d(TAG, "UVC Camera opened successfully")
                runOnUiThread {
                    binding.statusText.text = "UVC Camera Connected"
                    Toast.makeText(this, "UVC Camera Connected", Toast.LENGTH_SHORT).show()
                }
            }
            ICameraStateCallBack.State.CLOSED -> {
                Log.d(TAG, "UVC Camera closed")
                runOnUiThread {
                    binding.statusText.text = "UVC Camera Disconnected"
                }
            }
            ICameraStateCallBack.State.ERROR -> {
                Log.e(TAG, "UVC Camera error: $msg")
                runOnUiThread {
                    binding.statusText.text = "UVC Error: $msg"
                    Toast.makeText(this, "Camera Error: $msg", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun getGravity(): Int {
        return Gravity.CENTER
    }
}
