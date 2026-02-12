package com.checkus.uvcdemo

import android.Manifest
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

/**
 * Simple UVC Camera Preview Demo
 *
 * This activity demonstrates how to display a UVC camera stream using
 * the AndroidUSBCamera library (com.jiangdg.ausbc).
 *
 * For Q-Face Pro, the UVC camera provides the visible preview while
 * the IR camera communication is handled separately via USB serial.
 */
class MainActivity : CameraActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        private const val TAG = "UvcCameraDemo"
        private const val PERMISSION_REQUEST_CODE = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: UVC Camera Demo started")
        checkAndRequestPermissions()
    }

    override fun onResume() {
        super.onResume()
        // Check for connected USB devices
        checkConnectedDevices()
    }

    private fun checkConnectedDevices() {
        val devices = getDeviceList() ?: emptyList()
        Log.d(TAG, "Connected USB devices: ${devices.size}")
        devices.forEachIndexed { index, device ->
            Log.d(TAG, "Device $index: ${device.deviceName}, VID=${device.vendorId}, PID=${device.productId}")
        }

        if (devices.isEmpty()) {
            binding.statusText.text = "No USB cameras detected"
        } else {
            binding.statusText.text = "Found ${devices.size} USB device(s)"
            // Request permission for the first device if not already open
            if (!isCameraOpened() && devices.isNotEmpty()) {
                Log.d(TAG, "Requesting permission for device: ${devices[0].deviceName}")
                requestPermission(devices[0])
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
                Toast.makeText(this, "Camera permission is required for UVC camera", Toast.LENGTH_LONG).show()
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
        // Configure camera preview settings
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
                Log.d(TAG, "Camera opened successfully")
                runOnUiThread {
                    binding.statusText.text = "Camera Connected"
                    Toast.makeText(this, "UVC Camera Connected", Toast.LENGTH_SHORT).show()
                }
            }
            ICameraStateCallBack.State.CLOSED -> {
                Log.d(TAG, "Camera closed")
                runOnUiThread {
                    binding.statusText.text = "Camera Disconnected"
                }
            }
            ICameraStateCallBack.State.ERROR -> {
                Log.e(TAG, "Camera error: $msg")
                runOnUiThread {
                    binding.statusText.text = "Error: $msg"
                    Toast.makeText(this, "Camera Error: $msg", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun getGravity(): Int {
        return Gravity.CENTER
    }
}
