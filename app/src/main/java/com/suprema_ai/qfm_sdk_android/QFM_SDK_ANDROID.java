package com.suprema_ai.qfm_sdk_android;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.suprema_ai.qfm_sdk_android.callback_interface.*;
import com.suprema_ai.qfm_sdk_android.enumeration.*;
import com.suprema_ai.qfm_sdk_android.structure.*;
import com.suprema_ai.qfm_sdk_android.message_handler.MessageHandler;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

public class QFM_SDK_ANDROID extends QFM_SDK_CALLBACK_INTERFACE {
    static {
        System.loadLibrary("native-lib");
    }

    //region Implementations of callback functions from Java
    private SetupSerialCallback setupSerialCallback = new SetupSerialCallback() {
        @Override
        public void callback(int baudrate) {
            if (usbService != null)
                usbService.setBuadrate(baudrate);
        }
    };

    private ReadSerialCallback readSerialCallback = new ReadSerialCallback() {
        @Override
        public int callback(byte[] data, int size, int timeout) {
            int ret = usbService.readSerial(data, timeout);

            return ret;
        }
    };

    private WriteSerialCallback writeSerialCallback = new WriteSerialCallback() {
        @Override
        public int callback(byte[] data, int size, int timeout) {

            int ret = usbService.writeSerial(data, timeout);
            return ret;
        }
    };
    
    public SendPacketCallback sendPacketCallback = new SendPacketCallback() {
        @Override
        public void callback(byte[] data) {

        }
    };

    public ReceivePacketCallback receivePacketCallback = new ReceivePacketCallback() {
        @Override
        public void callback(byte[] data) {

        }
    };

    public SendRawDataCallback sendRawDataCallback = new SendRawDataCallback() {
        @Override
        public void callback(int writtenLen, int totalSize) {

        }
    };

    public ReceiveRawDataCallback receiveRawDataCallback = new ReceiveRawDataCallback() {
        @Override
        public void callback(int readLen, int totalSize) {

        }
    };

    public SendDataPacketCallback sendDataPacketCallback = new SendDataPacketCallback() {
        @Override
        public void callback(int index, int numOfPacket) {

        }
    };

    public ReceiveDataPacketCallback receiveDataPacketCallback = new ReceiveDataPacketCallback() {
        @Override
        public void callback(int index, int numOfPacket) {

        }
    };

    public UserInfoCallback userInfoCallback = new UserInfoCallback() {
        @Override
        public void callback(int index, int numOfTemplate) {

        }
    };

    public ScanCallback scanCallback = new ScanCallback() {
        @Override
        public void callback(byte errCode) {

        }
    };

    public EnrollCallback enrollCallback = new EnrollCallback() {
        @Override
        public void callback(byte errCode, QF_ENROLL_MODE enrollMode, int numOfSuccess) {

        }
    };

    public IdentifyCallback identifyCallback = new IdentifyCallback() {
        @Override
        public void callback(byte errCode) {

        }
    };

    public VerifyCallback verifyCallback = new VerifyCallback() {
        @Override
        public void callback(byte errCode) {

        }
    };

    public DeleteCallback deleteCallback = new DeleteCallback() {
        @Override
        public void callback(byte errCode) {

        }
    };

    public UserFeedbackCallback userfeedbackCallback = new UserFeedbackCallback() {
        @Override
        public void callback(int errCode) {

        }
    };

    public UserFeedbackDataCallback userfeedbackDataCallback = new UserFeedbackDataCallback() {
        @Override
        public void callback(UserFeedbackData feedbackData, Object userData) {

        }
    };

    public static MsgCallback msgCallback = new MsgCallback() {
        @Override
        public boolean callback(byte message) {
            return false;
        }
    };
    // endregion



    // region
    private native void _SetupSerialCallback(SetupSerialCallback callback);
    private native void _ReadSerialCallback(ReadSerialCallback callback);
    private native void _WriteSerialCallback(WriteSerialCallback callback);
    private native void _SendPacketCallback(SendPacketCallback callback);
    private native void _ReceivePacketCallback(ReceivePacketCallback callback);
    private native void _SendRawDataCallback(SendRawDataCallback callback);
    private native void _ReceiveRawDataCallback(ReceiveRawDataCallback callback);
    private native void _SendDataPacketCallback(SendDataPacketCallback callback);
    private native void _ReceiveDataPacketCallback(ReceiveDataPacketCallback callback);
    private native void _UserInfoCallback(UserInfoCallback callback);
    private native void _ScanCallback(ScanCallback callback);
    private native void _EnrollCallback(EnrollCallback callback);
    private native void _IdentifyCallback(IdentifyCallback callback);
    private native void _VerifyCallback(VerifyCallback callback);
    private native void _DeleteCallback(DeleteCallback callback);
    private native void _UserFeedbackCallback(UserFeedbackCallback callback);
    private native void _UserFeedbackDataCallback(final UserFeedbackDataCallback callback, Object userData);
    // endregion




    // region Registering functions for callback functions from Java

    /**
     * Sets the callback function of changing baudrate.
     *
     * @param callback The callback function.
     */
    public void QF_SetSetupSerialCallback(SetupSerialCallback callback) {
        if (callback != null) {
            setupSerialCallback = callback;
            _SetupSerialCallback(setupSerialCallback);
        } else
            _SetupSerialCallback(null);
    }

    /**
     * Sets the callback function of reading the serial data from the android system.
     *
     * @param callback The callback function.
     */
    public void QF_SetReadSerialCallback(ReadSerialCallback callback) {
        if (callback != null) {
            readSerialCallback = callback;
            _ReadSerialCallback(readSerialCallback);
        } else
            _ReadSerialCallback(null);
    }

    /**
     * Sets the callback function of writing the serial data from the android system.
     *
     * @param callback The callback function.
     */
    public void QF_SetWriteSerialCallback(WriteSerialCallback callback) {
        if (callback != null) {
            writeSerialCallback = callback;
            _WriteSerialCallback(writeSerialCallback);
        } else
            _WriteSerialCallback(null);
    }

    /**
     * Sets the callback function of sending packets.
     *
     * @param callback The callback function.
     */
    public void QF_SetSendPacketCallback(SendPacketCallback callback) {
        if (callback != null) {
            sendPacketCallback = callback;
            _SendPacketCallback(sendPacketCallback);
        } else
            _SendPacketCallback(null);

    }

    /**
     * Sets the callback function of receiving packets.
     *
     * @param callback The callback function.
     */
    public void QF_SetReceivePacketCallback(ReceivePacketCallback callback) {
        if (callback != null) {
            receivePacketCallback = callback;
            _ReceivePacketCallback(receivePacketCallback);
        } else
            _ReceivePacketCallback(null);
    }

    /**
     * Sets the callback function of sending raw data.
     *
     * @param callback The callback function.
     */
    public void QF_SetSendRawDataCallback(SendRawDataCallback callback) {
        if (callback != null) {
            sendRawDataCallback = callback;
            _SendRawDataCallback(sendRawDataCallback);
        } else
            _SendRawDataCallback(null);
    }

    /**
     * sets the callback function of receiving raw data.
     *
     * @param callback The callback function.
     */
    public void QF_SetReceiveRawDataCallback(ReceiveRawDataCallback callback) {
        if (callback != null) {
            receiveRawDataCallback = callback;
            _ReceiveRawDataCallback(receiveRawDataCallback);
        } else
            _ReceiveRawDataCallback(null);
    }

    /**
     * Sets the callback function of sending data packets.
     *
     * @param callback The callback function.
     */
    public void QF_SetSendDataPacketCallback(SendDataPacketCallback callback) {
        if (callback != null) {
            sendDataPacketCallback = callback;
            _SendDataPacketCallback(sendDataPacketCallback);
        } else
            _SendDataPacketCallback(null);
    }

    /**
     * Sets the callback function of receiving data packets.
     *
     * @param callback The callback function.
     */
    public void QF_SetReceiveDataPacketCallback(ReceiveDataPacketCallback callback) {
        if (callback != null) {
            receiveDataPacketCallback = callback;
            _ReceiveDataPacketCallback(receiveDataPacketCallback);
        } else
            _ReceiveDataPacketCallback(null);

    }

    /**
     * sets the callback function for getting user information.
     *
     * @param callback The callback function.
     */
    public void QF_SetUserInfoCallback(UserInfoCallback callback) {
        if (callback != null) {
            userInfoCallback = callback;
            _UserInfoCallback(userInfoCallback);
        } else
            _UserInfoCallback(null);
    }

    /**
     * sets the callback function for scanning face
     *
     * @param callback The callback function.
     */
    public void QF_SetScanCallback(ScanCallback callback) {
        if (callback != null) {
            scanCallback = callback;
            _ScanCallback(scanCallback);
        } else
            _ScanCallback(null);
    }

    /**
     * Sets the callback function for enrollment process.
     *
     * @param callback The callback function.
     */
    public void QF_SetEnrollCallback(EnrollCallback callback) {
        if (callback != null) {
            enrollCallback = callback;
            _EnrollCallback(enrollCallback);
        } else
            _EnrollCallback(null);
    }

    /**
     * Sets the callback function for identification.
     *
     * @param callback The callback function.
     */
    public void QF_SetIdentifyCallback(IdentifyCallback callback) {
        if (callback != null) {
            identifyCallback = callback;
            _IdentifyCallback(identifyCallback);
        } else
            _IdentifyCallback(null);
    }

    /**
     * Sets the callback function for verification process.
     *
     * @param callback The callback function.
     */
    public void QF_SetVerifyCallback(VerifyCallback callback) {
        if (callback != null) {
            verifyCallback = callback;
            _VerifyCallback(verifyCallback);
        } else
            _VerifyCallback(null);
    }

    /**
     * Sets the callback function for delete process.
     *
     * @param callback The callback function.
     */
    public void QF_SetDeleteCallback(DeleteCallback callback) {
        if (callback != null) {
            deleteCallback = callback;
            _DeleteCallback(deleteCallback);
        } else
            _DeleteCallback(null);
    }

    /**
     * Sets the callback function for user feedback.
     *
     * @param callback The callback function.
     */
    public void QF_SetUserFeedbackCallback(UserFeedbackCallback callback) {
        if (callback != null) {
            userfeedbackCallback = callback;
            _UserFeedbackCallback(userfeedbackCallback);
        } else
            _UserFeedbackCallback(null);
    }

    /**
     * Sets the callback function for user feedback.
     *
     * @param callback The callback function.
     * @param userData userData
     */
    public void QF_SetUserFeedbackDataCallback(UserFeedbackDataCallback callback, Object userData) {
        if (callback != null) {
            userfeedbackDataCallback = callback;
            _UserFeedbackDataCallback(userfeedbackDataCallback, userData);
        } else
            _UserFeedbackDataCallback(null, null);
    }
    // endregion




    //region Callback functions calling from JNI
    private void cbSetupSerial(int baudrate) {
        if (setupSerialCallback != null)
            setupSerialCallback.callback(baudrate);
    }

    private int cbReadSerial(byte[] data, int timeout) throws UnsupportedEncodingException {
        int ret = 0;
        if (readSerialCallback != null) {
            ret = readSerialCallback.callback(data, data.length, timeout);
        }
        return ret;
    }

    private int cbWriteSerial(byte[] data, int timeout) throws UnsupportedEncodingException {
        int ret = 0;

        if (writeSerialCallback != null) {
            ret = writeSerialCallback.callback(data, data.length, timeout);
        }
        return ret;
    }

    private void cbSendPacket(byte[] data) {
        if (sendPacketCallback != null)
            sendPacketCallback.callback(data);
    }

    private void cbReceivePacket(byte[] data) {
        if (receivePacketCallback != null)
            receivePacketCallback.callback(data);
    }

    private void cbSendRawData(int writtenLen, int totalSize) {
        if (sendRawDataCallback != null)
            sendRawDataCallback.callback(writtenLen, totalSize);
    }

    private void cbReceiveRawData(int readLen, int totalSize) {
        if (receiveRawDataCallback != null)
            receiveRawDataCallback.callback(readLen, totalSize);
    }

    private void cbSendDataPacket(int index, int numOfPacket) {
        if (sendDataPacketCallback != null)
            sendDataPacketCallback.callback(index, numOfPacket);
    }

    private void cbReceiveDataPacket(int index, int numOfPacket) {
        if (receiveDataPacketCallback != null)
            receiveDataPacketCallback.callback(index, numOfPacket);
    }

    private void cbUserInfo(int index, int numOfTemplate) {
        if (userInfoCallback != null)
            userInfoCallback.callback(index, numOfTemplate);
    }

    private void cbScan(byte errCode) {
        if (scanCallback != null)
            scanCallback.callback(errCode);
    }

    private void cbEnroll(byte errCode, QF_ENROLL_MODE enrollMode, int numOfSuccess) {
        if (enrollCallback != null)
            enrollCallback.callback(errCode, enrollMode, numOfSuccess);
    }

    private void cbIdentify(byte errCode) {
        if (identifyCallback != null)
            identifyCallback.callback(errCode);
    }

    private void cbVerify(byte errCode) {
        if (verifyCallback != null)
            verifyCallback.callback(errCode);
    }

    private void cbDelete(byte errCode) {
        if (deleteCallback != null)
            deleteCallback.callback(errCode);
    }

    private void cbUserFeedback(int errCode) {
        if (userfeedbackCallback != null)
            userfeedbackCallback.callback(errCode);
    }

    private void cbUserFeedbackData(UserFeedbackData feedbackData, Object userData) {
        if (userfeedbackDataCallback != null)
            userfeedbackDataCallback.callback(feedbackData, userData);
    }
    //endregion

    private UsbService usbService = null;
    private MessageHandler mHandler = null;
    private static AppCompatActivity mActivity = null;
    private static BroadcastReceiver mUsbReceiver = null;

    /**
     * Constructor
     */


    /**
     * Constructor of the QFM SDK for Android. (UART)
     */
    public QFM_SDK_ANDROID() {

    }

    /**
     * Constructor of the QFM SDK for Android. (USB)
     *
     * @param activity An instance of main activity. This is must be passed by the constructor.
     */
    public QFM_SDK_ANDROID(AppCompatActivity activity) {
        mActivity = activity;
    }

    /**
     * Constructor of the QFM SDK for Android. (USB)
     *
     * @param activity An instance of main activity. This is must be passed by the constructor.
     * @param handler  A handler which can bring the message of the SDK to the main activity.
     */
    public QFM_SDK_ANDROID(AppCompatActivity activity, MessageHandler handler) {
        mActivity = activity;
        mHandler = handler;

        // Set callback functions for using USB library.
        QF_SetSetupSerialCallback(setupSerialCallback);
        QF_SetWriteSerialCallback(writeSerialCallback);
        QF_SetReadSerialCallback(readSerialCallback);
    }

    /**
     * Constructor of the QFM SDK for Android. (USB)
     *
     * @param activity An instance of main activity. This is must be passed by the constructor.
     * @param handler  A handler which can bring the message of the SDK to the main activity.
     * @param receiver A receiver which can bring the broadcasting message such as
     *                 ACTION_USB_PERMISSION_GRANTED, ACTION_USB_DISCONNECTED and  etc. from the
     *                 UsbSerial library.
     */
    public QFM_SDK_ANDROID(AppCompatActivity activity, MessageHandler handler, BroadcastReceiver receiver) {
        mActivity = activity;
        mHandler = handler;
        mUsbReceiver = receiver;

        // Set callback functions for using USB library.
        QF_SetSetupSerialCallback(setupSerialCallback);
        QF_SetWriteSerialCallback(writeSerialCallback);
        QF_SetReadSerialCallback(readSerialCallback);
    }

    private final ServiceConnection usbConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName arg0, IBinder arg1) {
            if (mHandler != null) {
                usbService = ((UsbService.UsbBinder) arg1).getService();
                usbService.setHandler(mHandler);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            usbService = null;
        }
    };

    private void startService(Class<?> service, ServiceConnection serviceConnection, Bundle extras) {
        if (mHandler != null) {
            if (!UsbService.SERVICE_CONNECTED) {
                Intent startService = new Intent(mActivity, service);
                if (extras != null && !extras.isEmpty()) {
                    Set<String> keys = extras.keySet();
                    for (String key : keys) {
                        String extra = extras.getString(key);
                        startService.putExtra(key, extra);
                    }
                }
                mActivity.startService(startService);
            }
            Intent bindingIntent = new Intent(mActivity, service);
            mActivity.bindService(bindingIntent, serviceConnection, Context.BIND_AUTO_CREATE);
        }
    }

    private void setFilters() {
        if (mHandler != null) {
            IntentFilter filter = new IntentFilter();
            filter.addAction(UsbService.ACTION_USB_PERMISSION_GRANTED);
            filter.addAction(UsbService.ACTION_NO_USB);
            filter.addAction(UsbService.ACTION_USB_DISCONNECTED);
            filter.addAction(UsbService.ACTION_USB_NOT_SUPPORTED);
            filter.addAction(UsbService.ACTION_USB_PERMISSION_NOT_GRANTED);
            if (mUsbReceiver != null)
                mActivity.registerReceiver(mUsbReceiver, filter);
        }
    }

    /**
     * Opens a serial port and configures communication parameters. This function should be
     * called  first before calling any other APIs.
     *
     * @param baudrate  Specifies the baud rate at which the serial port operates. Available baud
     *                  rates are 9600, 19200, 38400, 57600, 115200bps, 230400bps,460800bps,
     *                  921600bps are available. The default setting of QFM modules is 115200bps.
     * @param asciiMode Determines the packet translation mode. If it is set to TRUE, the binary
     *                  packet is converted to ASCII format first before being sent to the module.
     *                  Response packets are in ASCII format, too. The default setting of QFM
     *                  modules  is binary mode.
     * @return If the function succeeds, return QF_RET_SUCCESS. Otherwise, return the
     * corresponding error code.
     */

    public QF_RET_CODE QF_InitCommPort(int baudrate, boolean asciiMode) {
        String commPort = null;
        QF_RET_CODE ret = QF_RET_CODE.QF_ERR_CANNOT_OPEN_SERIAL;
            //commPort = usbService.getUsbDeviceName();
        while (commPort == null) {
            System.out.println("Communication port is null, waiting...");
            commPort = usbService.getUsbDeviceName();
            if (commPort == null) {
                try {
                    Thread.sleep(100); // 1초 대기 후 다시 시도
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Communication port is not null, connecting...");
        ret = QF_InitCommPort(commPort, baudrate, asciiMode);

        return ret;
    }

    /**
     * Get Device Name. ( Serial Port Name )
     *
     * @return A string of device name.
     */
    public String QF_GetDeviceName() {
        String deviceName = null;
        if (usbService != null)
            deviceName = usbService.getUsbDeviceName();

        return deviceName;
    }

    public String QF_GetDevicePid() {
        String pid = null;
        if (usbService != null)
            pid = usbService.GetDevicePid();

        return pid;
    }
    public String QF_GetDeviceVid() {
        String vid = null;
        if (usbService != null)
            vid = usbService.GetDeviceVid();

        return vid;
    }


    /**
     * Resume Usb Service.
     */
    public void resumeService() {
        if (mHandler != null) {
            setFilters();  // Start listening notifications from UsbService
            startService(UsbService.class, usbConnection, null); // Start UsbService(if it was not started before) and Bind it
        }

    }

    /**
     * Pause Usb Service.
     */
    public void pauseService() {
        if (mHandler != null) {
            if (mUsbReceiver != null)
                mActivity.unregisterReceiver(mUsbReceiver);
            mActivity.unbindService(usbConnection);
        }
    }

    /**
     * Native Functions
     */

    public native int QF_GetPacketValue(int component, byte[] packet);

    /**
     * @brief Get SDK version
     *
     * @param major a pointer to major version
     * @param minor a pointer to minor version
     * @param revision a pointer to revision
     */
    public native void QF_GetSDKVersion(int[] major, int[] minor, int[] revision);

    /**
     * @brief Get SDK version string
     *
     * @return const char* version string
     */
    public native String QF_GetSDKVersionString();

    public native QF_RET_CODE QF_InitCommPort(String commPort, int baudrate, boolean asciiMode);
    public native QF_RET_CODE QF_CloseCommPort();
    public native void QF_Reconnect();
    public native QF_RET_CODE QF_SetBaudrate(int baudrate);

    /**
     * 2.3 Socket API
     */

    /**
     * @brief Initializes the socket interface and connects to the module with specified IP address.
     *
     * @param inetAddr IP address of the Ethernet-to-Serial converter.
     * @param port TCP/IP port of the socket interface.
     * @param asciiMode Determines the packet translation mode.
     *                  If it is set to TRUE, the binary packet is converted to ASCII format first before being sent to the module.
     *                  Response packets are in ASCII format, too.
     *                  The default setting of QFM modules is binary mode.
     * @return If the function succeeds, return QF_RET_SUCCESS. Otherwise, return the corresponding error code.
     */
    public native QF_RET_CODE QF_InitSocket(String inetAddr, int port, boolean asciiMode);

    /**
     * @brief Closes the socket interface
     *
     * @return If the function succeeds, return QF_RET_SUCCESS.
     *         Otherwise, return the corresponding error code.
     */
    public native QF_RET_CODE QF_CloseSocket();

    /**
     * 2.4 Basic Packet Interface API (Low-Level Packet API)
     */

    /**
     * @brief Sends a 13 byte packet to the module. The packet is composed as follows
     * @par Descriptions
     * | Start code | Command |  Param  |   Size  | Flag/Error | Checksum | End code |
     * |------------|---------|---------|---------|------------|----------|----------|
     * |   1 byte   | 1 byte  | 4 bytes | 4 bytes |   1 byte   |  1 byte  |  1 byte  |
     *
     * - Start code: 1 byte. Indicates the beginning of a packet. It always should be 0x40.
     * - Command: 1 byte. Refer to the Packet Protocol Manual for available commands.
     * - Param: 4 bytes. The meaning of this field varies according to each command.
     * - Size: 4 bytes. The meaning of this field varies according to each command.
     * - Flag/Error: 1 byte. Indicates flag data in the request packet, and error code in the response packet.
     * - Checksum: 1 byte. Checks the validity of a packet. Checksum is a remainder of the sum of each field, from the Start code to Flag/Error, divided by 256 (0x100).
     * - End code: 1 byte. Indicates the end of a packet. It always should be 0x0A. It is also used as a code indicating the end of binary data such as face templates.
     *
     * @param command Command field of a packet.
     *                Refer to the Packet Protocol Manual for available commands.
     * @param param Param field of a packet.
     * @param size Size field of a packet.
     * @param flag Flag/Error field of a packet.
     * @param timeout Sets the timeout in milliseconds.
     *                If sending does not complete within this limit, QF_ERR_WRITE_SERIAL_TIMEOUT will be returned.
     * @return If the function succeeds, return QF_RET_SUCCESS.
     *         Otherwise, return the corresponding error code.
     */
    public native QF_RET_CODE QF_SendPacket(byte command, int param, int size, byte flag, int timeout);

    /**
     * @brief Receives a 13 byte packet from the module.
     *        Most commands of Packet Protocol can be implemented by a pair of QF_SendPacket / QF_ReceivePacket.
     *
     * @param packet Pointer to the 13 byte packet.
     * @param timeout Sets the timeout in milliseconds.
     *                If receiving does not complete within this limit, QF_ERR_READ_SERIAL_TIMEOUT will be returned.
     * @return If the function succeeds, return QF_RET_SUCCESS.
     *         Otherwise, return the corresponding error code.
     */
    public native QF_RET_CODE QF_ReceivePacket(byte[] packet, int timeout);

    /**
     * @brief Some commands such as ET(Enroll Template) and IT(Identify Template) send additional data after the 13 byte request packet.
     *        QF_SendRawData is used in these cases for sending the data.
     *
     * @param buf Pointer to a data buffer.
     * @param size Number of bytes to be sent.
     * @param timeout Sets the timeout in milliseconds.
     *                If sending does not complete within this limit, QF_ERR_WRITE_SERIAL_TIMEOUT will be returned.
     * @return If the function succeeds, return QF_RET_SUCCESS.
     *         Otherwise, return the corresponding error code.
     */
    public native QF_RET_CODE QF_SendRawData(byte[] buf, int size, int timeout);

    /**
     * @brief Some commands such as ST(Scan Template) and RT(Read Template) return additional data after the 13 byte response packet.
     *        QF_ReceiveRawData is used in these cases for receiving the data.
     *
     * @param buf Pointer to a data buffer.
     * @param size Number of bytes to be received.
     * @param timeout Sets the timeout in milliseconds.
     *                If receiving does not complete within this limit, QF_ERR_READ_SERIAL_TIMEOUT will be returned.
     * @param checkEndCode Data transfer ends with ‘0x0a’.
     *                     If this parameter is FALSE, the function returns without checking the end code.
     * @return If the function succeeds, return QF_RET_SUCCESS.
     *         Otherwise, return the corresponding error code.
     */
    public native QF_RET_CODE QF_ReceiveRawData(byte[] buf, int size, int timeout, boolean checkEndCode);

    /**
     * @brief Sends data using Extended Data Transfer Protocol.
     *        Dividing large data into small blocks can reduce communication errors between the host and the module.
     *        Extended Data Transfer Protocol is an extension of Packet Protocol to provide a reliable and customizable communication for large data.
     *        In Extended Data Transfer Protocol, data is divided into multiple data packets.
     *        And a data packet consists of fixed-length header, variable-length data body, and 4 byte checksum.
     *        Commands which use the Extended Data Transfer Protocols are EIX, VIX, IIX, RIX, SIX, and UG.
     *
     * @param command Command field of a packet.
     *                Valid commands are EIX, VIX, IIX, RIX, SIX, and UG.
     * @param buf Pointer to a data buffer.
     * @param dataSize Number of bytes to be sent.
     * @param dataPacketSize Size of data packet.
     *                       For example, if dataSize is 16384 bytes and dataPacketSize is 4096 bytes, the data will be divided into 4 data packets.
     * @return If the function succeeds, return QF_RET_SUCCESS.
     *         Otherwise, return the corresponding error code.
     */
    public native QF_RET_CODE QF_SendDataPacket(byte command, byte[] buf, int dataSize, int dataPacketSize);

    /**
     * @brief Receives data using Extended Data Transfer Protocol.
     *        The size of data packet should be specified before calling this function.
     *
     * @param command Command field of a packet.
     *                Valid commands are EIX, VIX, IIX, RIX, SIX, and UG.
     * @param buf Pointer to a data buffer.
     * @param dataSize Number of bytes to be received.
     * @return If the function succeeds, return QF_RET_SUCCESS.
     *         Otherwise, return the corresponding error code.
     */
    public native QF_RET_CODE QF_ReceiveDataPacket(byte command, byte[] buf, int dataSize);

    /**
     * @brief Sets the size of data packets used in Extended Data Transfer protocol.
     *        The default value is 4096.
     *
     * @param defaultSize Size of data packet.
     */
    public native void QF_SetDefaultPacketSize(int defaultSize);

    /**
     * @brief Returns the size of data packet used in Extended Data Transfer protocol.
     *
     * @return The size of data packet.
     */
    public native int QF_GetDefaultPacketSize();

    /**
     * 2.5 Generic Command API
     */

    /**
     * @brief Encapsulates the commands composed of one request packet and one response packet.
     *        The majority of commands can be implemented using QF_Command.
     *
     * @param command Command field of a packet.
     *                Refer to the Packet Protocol Manual for available commands.
     * @param param Param field of a packet.
     *              This parameter is used both for input and output.
     * @param size Size field of a packet.
     *             This parameter is used both for input and output.
     * @param flag Flag field of a packet.
     *             This parameter is used both for input and output.
     * @return If packets are transferred successfully, return QF_RET_SUCCESS.
     *         Otherwise, return the corresponding error code.
     *         QF_RET_SUCCESS only means that request packet is received successfully.
     *         To know if the operation succeeds, the flag field should be checked.
     */
    public native QF_RET_CODE QF_Command(byte command, int[] param, int[] size, byte[] flag);

    /**
     * @brief Encapsulates the commands composed of one request packet and multiple response packets.
     *        Command such as ES(Enroll) and IS(Identify) can have more than one response packet.
     *        To handle these cases, QF_CommandEx requires a message callback function, which should return TRUE when the received packet is the last one.
     *
     * @param command Command field of a packet. Refer to the Packet Protocol Manual for available commands.
     * @param param Param field of a packet.
     *              This parameter is used both for input and output.
     * @param size Size field of a packet.
     *             This parameter is used both for input and output.
     * @param flag Flag field of a packet.
     *             This parameter is used both for input and output.
     * @param callback Pointer to the callback function.
     *                    This callback is called when a response packet is received.
     *                    If the callback return TRUE, QF_CommandEx will return immediately.
     *                    If the callback return FALSE, QF_CommandEx will wait for another response packet.
     * @return If packets are transferred successfully, return QF_RET_SUCCESS.
     *         Otherwise, return the corresponding error code.
     *         QF_RET_SUCCESS only means that request packet is received successfully.
     *         To know if the operation succeeds, the flag field should be checked.
     */
    public native QF_RET_CODE QF_CommandEx(byte command, int[] param, int[] size, byte[] flag, MsgCallback callback);

    /**
     * @brief Encapsulates the commands which send additional data after a request packet.
     *        For example, IT(Identify by Template) command should send template data after the request packet.
     *
     * @param command Command field of a packet.
     *                Refer to the Packet Protocol Manual for available commands.
     * @param param Param field of a packet.
     *              This parameter is used both for input and output.
     * @param size Size field of a packet.
     *             This parameter is used both for input and output.
     * @param flag Flag field of a packet.
     *             This parameter is used both for input and output.
     * @param data Pointer to the data buffer to be sent.
     * @param dataSize Number of bytes to be sent.
     * @return If packets are transferred successfully, return QF_RET_SUCCESS.
     *         Otherwise, return the corresponding error code.
     *         QF_RET_SUCCESS only means that request packet is received successfully.
     *         To know if the operation succeeds, the flag field should be checked.
     */
    public native QF_RET_CODE QF_CommandSendData(byte command, int[] param, int[] size, byte[] flag, byte[] data, int dataSize);

    /**
     * @brief Encapsulates the commands which send additional data and have multiple response packets.
     *        For example, ET(Enroll by Template) command sends template data after request packet and can have multiple response packets.
     *
     * @param command Command field of a packet.
     *                Refer to the Packet Protocol Manual for available commands.
     * @param param Param field of a packet.
     *              This parameter is used both for input and output.
     * @param size Size field of a packet.
     *             This parameter is used both for input and output.
     * @param flag Flag field of a packet.
     *             This parameter is used both for input and output.
     * @param data Pointer to the data buffer to be sent.
     * @param dataSize Number of bytes to be sent.
     * @param callback Pointer to the callback function.
     *                    This callback is called when a response packet is received.
     *                    If the callback return TRUE, QF_CommandSendDataEx will return immediately.
     *                    If the callback return FALSE, QF_CommandSendDataEx will wait for another response packet.
     * @param waitUserInput TRUE if the command needs user input. Otherwise, FALSE.
     * @return If packets are transferred successfully, return QF_RET_SUCCESS.
     *         Otherwise, return the corresponding error code.
     *         QF_RET_SUCCESS only means that request packet is received successfully.
     *         To know if the operation succeeds, the flag field should be checked.
     */
    public native QF_RET_CODE QF_CommandSendDataEx(byte command, int[] param, int[] size, byte[] flag, byte[] data, int dataSize, MsgCallback callback, boolean waitUserInput);

    /**
     * @brief Cancels the command which is being processed by the module.
     *        When the module is executing a command which needs user input to proceed, the status of the module will be changed to busy and the module returns QF_ERR_BUSY.
     *        If users want to execute another command before finishing the current one, they can explicitly cancel it by this function.
     *
     * @param receivePacket If TRUE, QF_Cancel waits until the response packet is received.
     *                      If FALSE, QF_Cancel returns immediately after sending the request packet.
     * @return If the function succeeds, return QF_RET_SUCCESS.
     *         Otherwise, return the corresponding error code.
     */
    public native QF_RET_CODE QF_Cancel(boolean receivePacket);

    /**
     * @brief Sets the timeout for generic commands.
     *        The default timeout is 10,000 ms (10s).
     *
     * @param timeout Specifies the timeout period in milliseconds.
     */
    public native void QF_SetGenericCommandTimeout(int timeout);

    /**
     * @brief Sets the timeout for commands which need user input.
     *        The default timeout is 10,000ms (10s).
     *
     * @param timeout
     */
    public native void QF_SetInputCommandTimeout(int timeout);

    /**
     * @brief Gets the timeout for generic commands.
     *
     * @return Timeout for generic commands.
     */
    public native int QF_GetGenericCommandTimeout();

    /**
     * @brief Gets the timeout for commands which need user input.
     *
     * @return Specifies the timeout period in milliseconds.
     */
    public native int QF_GetInputCommandTimeout();

    /**
     * 2.6 Module API
     */

    /**
     * @brief Retrieves the type, version and hardware revision information of the module.
     *
     * @param /type Availeable types are as follows:
     *
    |      Value    |    Description    |
    | ------------- | ----------------- |
    | QF_MODULE_PRO | Q-Face Pro series |
     * @param /version Version number of the module.
     * @param /hardware_revision Hardware revision information of the module.
     *
    |         Value          | Description |
    | ---------------------- | ----------- |
    | QF_HARDWARE_REVISION_A | Revision A  |
     * @return If the function succeeds, return QF_RET_SUCCESS.
     *         Otherwise, return the corresponding error code.
     */
    public native QF_RET_CODE QF_GetModuleInfo(QFModuleInfo info);

    /**
     * @brief Retrieves a string that describes the module information.
     *        This function should be called after QF_GetModuleInfo.
     *
     * @param type Specifies the type of the module.
     * @param version Specifies the version number of the module.
     * @param hw_revision Specifies hardware revision information of the module.
     * @return char* Null-terminated string that describes the module information.
     * @warning This pointer is a static data in the SDK.
     *          So, it should not be shared or freed by applications.
     */
    public native String QF_GetModuleString(QF_MODULE_TYPE type, QF_MODULE_VERSION version, QF_HARDWARE_REVISION hw_revision);

    /**
     * @brief Retrieves a string that describes the module information. This function calls \ref QF_GetModuleInfo and \ref QF_GetModuleString internally.
     *
     * @return Null-terminated string that describes the module information.
     */
    public native String QF_GetModuleString2();

    /**
     * @brief Search a module connected to the specified IP address.
     *        If it finds any module, it will return the communication parameters and the module ID.
     *
     * @param inetAddr IP address.
     * @param tcpPort TCP port.
     * @param asciiMode Pointer to the packet translation mode to be returned.
     * @param protocol Pointer to the protocol type to be returned.
     * @param moduleID Pointer to the module ID to be returned.
     * @return If it finds a module, return QF_RET_SUCCESS.
     *         If the search fails, return QF_ERR_NOT_FOUND.
     *         Otherwise, return the corresponding error code.
     * @remark This function is not supported yet.
     */
    public native QF_RET_CODE QF_SearchModuleBySocket(final String inetAddr, int tcpPort, boolean[] asciiMode, QF_PROTOCOL[] protocol, int[] moduleID);

    /**
     * @brief Upgrades the firmware of the module.
     *        Users should not turn off the module when upgrade is in progress.
     *
     * @param firmwareFilename Null-terminated string that specifies the firmware file name.
     * @param dataPacketSize The packet size of firmware data.
     *                       If it is 16384, the firmware is divided into 16384 byte packets before transferring to the module.
     * @return If upgrade succeeds, return QF_RET_SUCCESS.
     *         Otherwise, return the corresponding error code.
     */
    public native QF_RET_CODE QF_Upgrade(final String firmwareFilename, int dataPacketSize);

    public native QF_RET_CODE QF_UpdatePatch(final String filename, int dataPacketSize);

    /**
     * @brief Gets the firmware version of the module.
     *
     * @param major major version
     * @param minor minor version
     * @param revision revision version
     * @return QF_RET_CODE if it succeeds, return QF_RET_SUCCESS. Otherwise, return the corresponding error code.
     */
    public native QF_RET_CODE QF_GetFirmwareVersion(int[] major, int[] minor, int[] revision);

    /**
     * @brief Resets the module.
     *
     * @return return QF_RET_SUCCESS.
     */
    public native QF_RET_CODE QF_Reset();

    /**
     * @brief Enter DFU mode for downloading IMAGE.
     *
     * @return If it succeeds, it returns \ref QF_RET_SUCCESS. Otherwise, it returns an error code.
     */
    public native QF_RET_CODE QF_EnterDFUMode();

    /**
     * 2.7 System Parameter API
     */

    /**
     * @brief To prevent redundant communication, the QFM SDK caches the system parameters previously read or written.
     *        QF_InitSysParameter clears this cache.
     *        It is called in QF_Reconnect.
     */
    public native void QF_InitSysParameter();

    /**
     * @brief Reads the value of a system parameter.
     *        And read values are cached in the SDK.
     *        However, read-only values such as QF_SYS_ENROLLED_TEMPLATES and QF_SYS_AVAILABLE_TEMPLATES are not cached.
     *        That is, they always read the values from the device.
     *
     * @param parameter System parameter to be read.
     * @param value Pointer to the value of the specified system parameter to be returned.
     * @return If the function succeeds, return QF_RET_SUCCESS.
     *         If there is no such parameter, return QF_ERR_NOT_FOUND.
     *         Otherwise, return the corresponding error code.
     */
    public native QF_RET_CODE QF_GetSysParameter(QF_SYS_PARAM parameter, int[] value);

    /**
     * @brief Writes the value of a system parameter.
     *        The parameter value is changed in memory only.
     *        To make the change permanent, QF_Save should be called after  this function.
     *
     * @param parameter System parameter to be written.
     * @param value Value of the system parameter.
     * @return If the function succeeds, return QF_RET_SUCCESS.
     *         If there is no such parameter, return QF_ERR_NOT_FOUND.
     *         Otherwise, return the corresponding error code.
     */
    public native QF_RET_CODE QF_SetSysParameter(QF_SYS_PARAM parameter, int value);

    /**
     * @brief Reads the values of multiple system parameters.
     *
     * @param parameterCount Number of system parameters to be read.
     * @param parameters Array of system parameters to be read.
     * @param values Array of the values of the specified system parameters to be read.
     * @return If the function succeeds, return QF_RET_SUCCESS.
     *         Otherwise, return the corresponding error code.
     */
    public native QF_RET_CODE QF_GetMultiSysParameter(int parameterCount, QF_SYS_PARAM[] parameters, int[] values);

    /**
     * @brief Writes the values of multiple system parameters.
     *        The parameter value is changed in memory only.
     *        To make the change permanent, QF_Save should be called.
     *
     * @param parameterCount Number of system parameters to be written.
     * @param parameters Array of system parameters to be written.
     * @param values Array of the values of the specified system parameters to be written.
     * @return If the function succeeds, return QF_RET_SUCCESS.
     *         Otherwise, return the corresponding error code.
     */
    public native QF_RET_CODE QF_SetMultiSysParameter(int parameterCount, QF_SYS_PARAM[] parameters, int[] values);

    /**
     * @brief Saves the system parameters into the flash memory.
     *
     * @return If the function succeeds, return QF_RET_SUCCESS.
     *         Otherwise, return the corresponding error code.
     * @note The system parameters are stored in the flash memory.
     *       To make the change permanent, this function should be called.
     */
    public native QF_RET_CODE QF_Save();

    /**
     * @brief Reset all of the system parameters of the module to the default value.
     *
     * @return If the function succeeds, return QF_RET_SUCCESS.
     *         Otherwise, return the corresponding error code.
     */
    public native QF_RET_CODE QF_ResetSysParameters();

    /**
     * @brief Reset all of the system parameters of the module to the default value.
     * @return If the function succeeds, return QF_RET_SUCCESS.
     *         Otherwise, return the corresponding error code.
     * @deprecated This function is deprecated.
     *             Use QF_ResetSysParameters() instead.
     */
    public native QF_RET_CODE QF_ResetSystemConfiguration();

    /**
     * @brief Reset all of the system parameters of the module to the default value.
     * @return If the function succeeds, return \ref QF_RET_SUCCESS. Otherwise, return the corresponding error code.
     * @deprecated This function is deprecated. Use \ref QF_ResetSysParameters() instead.
     */
    public native QF_RET_CODE QF_ResetSystemParameter();

    /**
     * 2.8. Template Management API
     */

    /**
     * @brief Gets the number of templates stored in the module.
     *
     * @param numOfTemplate Pointer to the number of templates to be returned.
     * @return If the function succeeds, return QF_RET_SUCCESS.
     *         Otherwise, return the corresponding error code.
     */
    public native QF_RET_CODE QF_GetNumOfTemplate(int[] numOfTemplate);

    /**
     * @brief Gets the template capacity of the module.
     *
     * @param maxNumOfTemplate Pointer to the template capacity to be returned.
     * @return If the function succeeds, return QF_RET_SUCCESS.
     *         Otherwise, return the corresponding error code.
     */
    public native QF_RET_CODE QF_GetMaxNumOfTemplate(int[] maxNumOfTemplate);

    /**
     * @brief Retrieves all the user and template information stored in the module.
     *
     * @param userInfo Array of QFUserInfo structures, which will store all the information.
     *                 This pointer should be preallocated large enough to store all the information.
     * @param numOfUser Pointer to the number of users to be returned.
     * @param numOfTemplate Pointer to the number of templates to be returned.
     * @return If the function succeeds, return QF_RET_SUCCESS.
     *         Otherwise, return the corresponding error code.
     */
    public native QF_RET_CODE QF_GetAllUserInfo(QFUserInfo[] userInfo, int[] numOfUser, int[] numOfTemplate);

    /**
     * @brief Retrieves all the user and template information stored in the module.
     *
     * @param userInfo Array of QFUserInfoEx structures, which will store all the information.
     *                 This pointer should be preallocated large enough to store all the information.
     * @param numOfUser Pointer to the number of users to be returned.
     * @param numOfTemplate Pointer to the number of templates to be returned.
     * @return If the function succeeds, return QF_RET_SUCCESS.
     *         Otherwise, return the corresponding error code.
     */
    public native QF_RET_CODE QF_GetAllUserInfoEx(QFUserInfoEx[] userInfo, int[] numOfUser, int[] numOfTemplate);

    /**
     * @brief Sorts an QFUserInfo array in ascending order of user ID.
     *
     * @param userInfo Array of QFUserInfo structures.
     * @param numOfUser Number of QFUserInfo.
     * @note This function is not supported in Java SDK.
     */
    public native void QF_SortUserInfo(QFUserInfo[] userInfo, int numOfUser);

    /**
     * @brief Checks if the specified user ID has enrolled templates.
     *
     * @param userID User ID.
     * @param numOfTemplate Pointer to the number of templates of the user ID to be returned.
     * @return If there are templates of the user ID, return QF_RET_SUCCESS.
     *         Otherwise, return the corresponding error code.
     */
    public native QF_RET_CODE QF_CheckTemplate(int userID, int[] numOfTemplate);

    /**
     * @brief Checks if the specified template exists in the module.
     *
     * @param templateData Pointer to the template data to be checked.
     * @param userID Pointer to the user ID to be returned.
     * @return If the template exists, return \ref QF_RET_SUCCESS. Otherwise, return the corresponding error code.
     */
    public native QF_RET_CODE QF_CheckTemplate2(byte[] templateData, int[] userID);

    /**
     * @brief Reads the templates of the specified user ID.
     *
     * @param userID User ID.
     * @param numOfTemplate Pointer to the number of templates of the user ID to be returned.
     * @param templateData Pointer to the template data to be returned.
     *                     This pointer should be preallocated large enough to store all the template data.
     * @return If the function succeeds, return QF_RET_SUCCESS.
     *         Otherwise, return the corresponding error code.
     */
    public native QF_RET_CODE QF_ReadTemplate(int userID, int[] numOfTemplate, byte[] templateData);

    /**
     * @brief Reads one template of the specified user ID.
     *
     * @param userID User ID.
     * @param subID Sub index of the template. It is between 0 and 9.
     * @param templateData Pointer to the template data to be returned.
     *                     This pointer should be preallocated large enough to store all the template data.
     * @return If the function succeeds, return QF_RET_SUCCESS.
     *         Otherwise, return the corresponding error code.
     */
    public native QF_RET_CODE QF_ReadOneTemplate(int userID, int subID, byte[] templateData);

    /**
     * @brief Scans a face on the sensor and receives the template of it.
     *
     * @param templateData Pointer to the template data to be returned.
     * @param templateSize Pointer to the template size to be returned.
     * @param imageQuality Pointer to the image quality score to be returned.
     *                     The score shows the quality of scanned face and is in the range of 0 ~ 100.
     * @return If the function succeeds, return QF_RET_SUCCESS.
     *         Otherwise, return the corresponding error code.
     */
    public native QF_RET_CODE QF_ScanTemplate(byte[] templateData, int[] templateSize, int[] imageQuality);

    /**
     * @brief Save user template database from the module to a file.
     *
     * @param filename Path of the file to save the database.
     * @return If the function succeeds, return QF_RET_SUCCESS.
     *         Otherwise, return the corresponding error code.
     * @note This function is not supported yet.
     */
    public native QF_RET_CODE QF_SaveDB(final String filename);

    /**
     * @brief Load user template database from a file to the module.
     *
     * @param filename Path of the file to load the database.
     * @return If the function succeeds, return QF_RET_SUCCESS.
     *         Otherwise, return the corresponding error code.
     * @note This function is not supported yet.
     */
    public native QF_RET_CODE QF_LoadDB(final String filename);

    /**
     * @brief Formats the user database of the module.
     *        Users should not turn off the module when format is in progress.
     *
     * @return If the function succeeds, return QF_RET_SUCCESS.
     *         Otherwise, return the corresponding error code.
     */
    public native QF_RET_CODE QF_ResetDB();

    /**
     * @brief Gets the number of users enrolled in the module.
     *
     * @param numOfUser the number of users to be returned.
     * @return If the function succeeds, return \ref QF_RET_SUCCESS. Otherwise, return the corresponding error code.
     */
    public native QF_RET_CODE QF_GetNumOfUser(int[] numOfUser);

    /**
     * @brief Formats the user database of the module.
     *        Users should not turn off the module when format is in progress.
     *
     * @return If the function succeeds, return QF_RET_SUCCESS.
     *         Otherwise, return the corresponding error code.
     * @deprecated This function is deprecated. Use QF_ResetDB instead.
     */
    public native QF_RET_CODE QF_FormatUserDatabase();

    /**
     * 2.9. Enroll API
     */

    /**
     * @brief Enrolls face inputs on the sensor.
     *        The enrollment process varies according to the QF_SYS_ENROLL_MODE system parameter.
     *        Also, see QF_ENROLL_MODE.
     *
     * Users can also fine tune the enrollment process by selecting one of the QF_ENROLL_OPTION.
     * @param userID User ID.
     * @param option Enroll option.
     * @param enrollID Pointer to the enrolled user ID.
     *                 This parameter can be different from userID when AUTO_ID option is used.
     * @param imageQuality Pointer to the image quality score to be returned.
     *                     The score shows the quality of scanned face and is in the range of 0 ~ 100.
     * @return If enroll succeeds, return QF_RET_SUCCESS.
     *         Otherwise, return the corresponding error code
     */
    public native QF_RET_CODE QF_Enroll(int userID, QF_ENROLL_OPTION option, int[] enrollID, int[] imageQuality);

    /**
     * @brief Enrolls a face template.
     *
     * @param userID User ID.
     * @param option Enroll option.
     * @param templateSize Size of the template data.
     * @param templateData Pointer to the template data.
     * @param enrollID Pointer to the enrolled user ID.
     *                 This parameter can be different from userID when QF_ENROLL_OPTION_AUTO_ID option is used.
     * @return If enroll succeeds, return QF_RET_SUCCESS.
     *         Otherwise, return the corresponding error code.
     */
    public native QF_RET_CODE QF_EnrollTemplate(int userID, QF_ENROLL_OPTION option, int templateSize, byte[] templateData, int[] enrollID);

    /**
     * @brief Enrolls multiple templates to the specified ID.
     *
     * @param userID User ID.
     * @param option Enroll option.
     * @param numOfTemplate Number of templates to be enrolled.
     * @param templateSize Size of one template data.
     *                     For example, when enroll 3 templates of 384 byte, this parameter is 384 not 1152.
     * @param templateData Pointer to the template data.
     * @param enrollID Pointer to the enrolled user ID.
     *                 This parameter can be different from userID when QF_ENROLL_OPTION_AUTO_ID option is used.
     * @return If enroll succeeds, return QF_RET_SUCCESS.
     *         Otherwise, return the corresponding error code.
     */
    public native QF_RET_CODE QF_EnrollMultipleTemplates(int userID, QF_ENROLL_OPTION option, int numOfTemplate, int templateSize, byte[] templateData, int[] enrollID);

    /**
     * @brief Enrolls a face image.
     *
     * @param userID User ID.
     * @param option Enroll option.
     * @param imageSize Size of the image data.
     * @param imageData Pointer to the raw image data.
     *                  Note that it is not the pointer to QFImage, but the pointer to the raw pixel data without the QFImage header.
     * @param enrollID Pointer to the enrolled user ID.
     *                 This parameter can be different from userID when QF_ENROLL_OPTION_AUTO_ID option is used.
     * @param imageQuality
     * @return If enroll succeeds, return QF_RET_SUCCESS.
     *         Otherwise, return the corresponding error code.
     */
    public native QF_RET_CODE QF_EnrollImage(int userID, QF_ENROLL_OPTION option, int imageSize, byte[] imageData, int[] enrollID, int[] imageQuality);

    /**
     * 2.10. Identify API
     */

    /**
     * @brief Identifies the face input on the sensor.
     *
     * @param userID Pointer to the user ID to be returned.
     * @param subID Pointer to the index of the template to be returned.
     * @return If matching succeeds, return QF_RET_SUCCESS.
     *         If matching fails, return QF_ERR_NOT_FOUND.
     *         Otherwise, return the corresponding error code.
     */
    public native QF_RET_CODE QF_Identify(int[] userID, byte[] subID);

    /**
     * @brief Identifies a template.
     *
     * @param templateSize Size of the template data.
     * @param templateData Pointer to the template data.
     * @param userID Pointer to the user ID to be returned.
     * @param subID Pointer to the index of the template to be returned.
     * @return If matching succeeds, return QF_RET_SUCCESS.
     *         If matching fails, return QF_ERR_NOT_FOUND.
     *         Otherwise, return the corresponding error code.
     */
    public native QF_RET_CODE QF_IdentifyTemplate(int templateSize, byte[] templateData, int[] userID, byte[] subID);

    /**
     * @brief Identifies a face image.
     *
     * @param imageSize Size of the image data.
     * @param imageData Pointer to the raw image data.
     *                  Note that it is not the pointer to QFImage, but the pointer to the raw pixel data without the QFImage header.
     * @param userID Pointer to the user ID to be returned.
     * @param subID Pointer to the index of the template to be returned.
     * @return If matching succeeds, return QF_RET_SUCCESS.
     *         If matching fails, return QF_ERR_NOT_FOUND.
     *         Otherwise, return the corresponding error code.
     */
    public native QF_RET_CODE QF_IdentifyImage(int imageSize, byte[] imageData, int[] userID, byte[] subID);

    /**
     * 2.11. Verify API
     */

    /**
     * @brief Verifies if a face input matches the enrolled faces of the specified user id.
     *
     * @param userID User ID.
     * @param subID Pointer to the index of the template to be returned.
     * @return If matching succeeds, return QF_RET_SUCCESS.
     *         If matching fails, return QF_ERR_NOT_MATCH.
     *         Otherwise, return the corresponding error code.
     */
    public native QF_RET_CODE QF_Verify(int userID, byte[] subID);

    /**
     * @brief Verifies a template.
     *
     * @param templateSize Size of the template data.
     * @param templateData Pointer to the template data to be sent.
     * @param userID User ID.
     * @param subID Pointer to the index of the template to be returned.
     * @return If matching succeeds, return QF_RET_SUCCESS.
     *         If matching fails, return QF_ERR_NOT_MATCH.
     *         Otherwise, return the corresponding error code.
     */
    public native QF_RET_CODE QF_VerifyTemplate(int templateSize, byte[] templateData, int userID, byte[] subID);

    /**
     * @brief Transmits face templates from the host to the module and verifies if they match the live face input.
     *
     * @param numOfTemplate Number of templates to be transferred to the module.
     * @param templateSize Size of a template.
     * @param templateData Pointer to the template data to be transferred to the module.
     * @return If matching succeeds, return QF_RET_SUCCESS.
     *         If matching fails, return QF_ERR_NOT_MATCH.
     *         Otherwise, return the corresponding error code.
     */
    public native QF_RET_CODE QF_VerifyHostTemplate(int numOfTemplate, int templateSize, byte[] templateData);

    /**
     * @brief Verifies a face image.
     *
     * @param imageSize Size of the face image.
     * @param imageData Pointer to the raw image data.
     *                  Note that it is not the pointer to QFImage, but the pointer to the raw pixel data without the QFImage header.
     * @param userID User ID.
     * @param subID Pointer to the index of the template to be returned.
     * @return If matching succeeds, return QF_RET_SUCCESS.
     *         If matching fails, return QF_ERR_NOT_MATCH.
     *         Otherwise, return the corresponding error code.
     */
    public native QF_RET_CODE QF_VerifyImage(int imageSize, byte[] imageData, int userID, byte[] subID);

    /**
     * 2.12. Delete API
     */

    /**
     * @brief Deletes the enrolled templates of the specified user ID.
     *
     * @param userID User ID.
     * @return If delete succeeds, return QF_RET_SUCCESS.
     *         Otherwise, return the corresponding error code.
     */
    public native QF_RET_CODE QF_Delete(int userID);

    /**
     * @brief Deletes one template of the specified user ID.
     *
     * @param userID User ID.
     * @param subID Sub index of the template.
     *              It is between 0 and 19.
     * @return If delete succeeds, return QF_RET_SUCCESS.
     *         Otherwise, return the corresponding error code.
     */
    public native QF_RET_CODE QF_DeleteOneTemplate(int userID, int subID);

    /**
     * @brief Deletes the enrolled templates of multiple user IDs.
     *
     * @param startUserID First user ID to be deleted.
     * @param lastUserID Last user ID to be deleted.
     * @param deletedUserID Pointer to the number of IDs to be actually deleted by the module.
     * @return If delete succeeds, return QF_RET_SUCCESS.
     *         Otherwise, return the corresponding error code.
     */
    public native QF_RET_CODE QF_DeleteMultipleTemplates(int startUserID, int lastUserID, int[] deletedUserID);

    /**
     * @brief Deletes all the templates stored in a module.
     *
     * @return If delete succeeds, return QF_RET_SUCCESS.
     *         Otherwise, return the corresponding error code.
     */
    public native QF_RET_CODE QF_DeleteAll();

    /**
     * 2.13. Image Manipulation API
     */

    /**
     * @brief Reads the last scanned face image which is an RGB channel image.
     *
     * @param image Pointer to the QFImage structure.
     * @return If the function succeeds, return QF_RET_SUCCESS. Otherwise, return the corresponding error code.
     * @note the image’s memory is automatically allocated after calling the QF_ReadImage().
     *       So release the memory using QF_ReleaseImage() if you don’t use the image on the program anymore.
     *       Also, if you use the pointer of QFImage, you should handle the memory allocation and release by yourself.
     *       If you use the pointer of QFImage, you should release the memory using free() after calling QF_ReleaseImage().
     */
    public native QF_RET_CODE QF_ReadImage(QFImage image);

    /**
     * @brief Scans a face image and retrieves the image of it.
     *
     * @param image Pointer to the QFImage structure.
     * @return If the function succeeds, return QF_RET_SUCCESS.
     *         Otherwise, return the corresponding error code.
     */
    public native QF_RET_CODE QF_ScanImage(QFImage image);

    /**
     * @brief Save a QFImage into the specified file in JPEG format.
     *
     * @param fileName Null-terminated string that specifies the file name.
     *                 Regardless of the file name, images are saved in JPEG format.
     * @param image Pointer to the QFImage to be saved.
     * @return If the function succeeds, return QF_RET_SUCCESS.
     *         Otherwise, return the corresponding error code.
     */
    public native QF_RET_CODE QF_SaveImage(final String fileName, QFImage image);

    /**
     * 2.14. Misc API
     */

    /**
     * @brief Reads QR code
     *
     * @param decodedText Pointer to the decoded text
     * @param decodedTextLength Length of the decoded text
     * @return QF_RET_CODE if the function succeeds, return \ref QF_RET_SUCCESS. Otherwise, return the corresponding error code.
     */
    public native QF_RET_CODE QF_ReadQRCode(byte[] decodedText, int[] decodedTextLength);

    /**
     * 2.15. Key Management API
     */

    /**
     * @brief change keys relevant to the cryptography.
     *
     * @param option option for changing key. this option is defined in \ref QF_KEY_OPTION in detail.
     * @param currentKey current key value. this value is used for verification of the key.
     * @param newKey new key value. this value is used for changing the key.
     * @return If the function succeeds, return \ref QF_RET_SUCCESS. Otherwise, return the corresponding error code.
     * @note
     * When you change the key for the first time, you should set this value to FALSE. if you change the key after the first time, you should set this value to TRUE to avoid the accidental key change.
     * If you lost your key, you can reset all keys using \ref QF_ResetKey() function.
     */
    public native QF_RET_CODE QF_ChangeKey(QF_KEY_OPTION option, byte[] currentKey, byte[] newKey);
    
    /**
     * @brief verify keys relevant to the cryptography. keys only can be verified under the secure packet protocol.
     * 
     * @param option \ref QF_KEY_OPTION_VERIFY_INITIALIZATION_VECTOR, \ref QF_KEY_OPTION_VERIFY_ENCRYPTION_KEY can be used.
     * @param currentKey current key value. this value is used for verification of the key.
     * @return If the function succeeds, return \ref QF_RET_SUCCESS. Otherwise, return the corresponding error code.
     * @warning
     * This function can be used only under the secure packet protocol. If you want to use this function, you should set the secure packet protocol mode to TRUE by using \ref QF_SetSecurePacketProtocolMode() function.
     * This function is very dangerous because the key can be exposed to the outside. So, you should make a logic to avoid multiple calls of this function in a short time.
     */
    public native QF_RET_CODE QF_VerifyKey(QF_KEY_OPTION option, byte[] currentKey);

    /**
     * @brief reset keys relevant to the cryptography.
     *
     * @param option option for resetting key. this option is defined in \ref QF_KEY_OPTION in detail.
     * @return if the function succeeds, return \ref QF_RET_SUCCESS. Otherwise, return the corresponding error code.
     */
    public native QF_RET_CODE QF_ResetKey(QF_KEY_OPTION option);

    /**
     * 2.16. Secure Packet Protocol API
     */

    /**
     * @brief Gets whether secure packet protocol mode is enabled or disabled.
     *
     * @return If secure packet protocol mode is enabled, it returns TRUE. Otherwise, it returns FALSE.
     */
    public native boolean QF_GetSecurePacketProtocolMode();

    /**
     * @brief Sets the secure packet protocol mode enabled or disabled.
     *
     * @param securePacketProtocolMode f it is set to TRUE, the secure packet protocol is enabled. If the secure packet protocol is enabled, users can communicate via the encrypted packets.
     * @param secureKey The secure key is an encryption key for packet encryption only. Q-Face series have two kinds of encryption keys. The first one is for data encryption such as templates and images, and the second one is for packet encryption. For discriminating confusion of the terms, the key for packet encryption is named a secure key.
    This key can be set 32byte length or NULL when the securePacketProtocolMode parameter is set to TRUE. If it is NULL, the secure key will be created randomly by using a public key exchange with the module.

     * @return BOOL
     */
    public native boolean QF_SetSecurePacketProtocolMode(boolean securePacketProtocolMode, byte[] secureKey);

    /**
     * @brief Sets the secure code for the secure packet protocol.
     *
     * @param secureCode The secure code is used to reach out to high-level security. Users using secure packet protocol can encrypt command protocol packets, including this secure code. When users sent the encrypted command protocol packets with their own secure code, the module returns the encrypted command protocol packets including the same secure code that users sent before. The secure code can be set to 8 bytes in length or NULL. If it is NULL, the secure code will be randomly changed for every packet communication pair about the request and response.
    If the secure code from the SFM is different from the code that the users sent, it can be doubted as an attack from strangers.
     */
    public native void QF_SetSecureCode(byte[] secureCode);

    /**
     * @brief Creates a random secure key for the secure packet protocol using the \ref QF_PublicKeyExchange. This key is used to communicate using a temporary encrypted secure packet.
     *
     * @return If the function succeeds, the return value is \ref QF_RET_SUCCESS. Otherwise, returns corresponding error code.
     */
    public native QF_RET_CODE QF_CreateRandomSecureKey();

    /**
     * @brief Creates a key pair (private key and public key) for the public key exchange. These key pair is used to make a secure key for communicating between host and the module. That is, when users use the secure packet protocol, they need a secure key for the command protocol packet encryption. The secure key can be randomly generated by using the public key exchange.
     *
     * @param publicKey_host Pointer to 32byte length public key generated for the host.
     * @param privateKey_host Pointer to 64byte length private key generated for the host.
     * @return If the function succeeds, the return value is \ref QF_RET_SUCCESS. Otherwise, return the corresponding error code.
     */
    public native QF_RET_CODE QF_CreateKeyPair(byte[] publicKey_host, byte[] privateKey_host);

    /**
     * @brief Gets a secure key from the public key of the module with the private key of the host. This function is called in \ref QF_CreateRandomSecureKey.
     *
     * @param secureKey Pointer to 32byte length secure key generated from the publicKey_module and the privateKey_host that created by the QF_PublicKeyExchange and QF_CreateKeyPair, respectively. This key is used to encrypt the command protocol packets.
     * @param publicKey_module Pointer to 32byte length public key generated from the module.
     * @param privateKey_host Pointer to 64byte length private key generated from the host.
     */
    public native void QF_GetSecureKey(byte[] secureKey, byte[] publicKey_module, byte[] privateKey_host);

    /**
     * @brief Exchanges the public key from the host and the module to each other. Users can get the public key generated from the module by using sending their public key generated from the host to the module. Also, users can get the secure key from their private key paired with the public key they sent via this function. Refer to \ref QF_GetSecureKey, \ref QF_CreateKeyPair.
     *
     * @param publicKey_host Pointer to 32byte length public key generated from the host.
     * @param publicKey_module Pointer to 32byte length public key generated from the module.
     * @return If the function succeeds, the return value is \ref QF_RET_SUCCESS. Otherwise, return the corresponding error code.
     */
    public native QF_RET_CODE QF_PublicKeyExchange(byte[] publicKey_host, byte[] publicKey_module);
}