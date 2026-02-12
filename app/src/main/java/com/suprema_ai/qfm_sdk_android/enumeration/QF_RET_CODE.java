/*
 * Copyright (c) 2001 - 2019. Suprema Inc. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for details.
 */

package com.suprema_ai.qfm_sdk_android.enumeration;

/**
 * Please refer to the QFM Protocol Manual for the details.
 */
public enum QF_RET_CODE {
    QF_RET_SUCCESS(0),

    // serial communication error
    QF_ERR_CANNOT_OPEN_SERIAL(-1),
    QF_ERR_CANNOT_SETUP_SERIAL(-2),
    QF_ERR_CANNOT_WRITE_SERIAL(-3),
    QF_ERR_WRITE_SERIAL_TIMEOUT(-4),
    QF_ERR_CANNOT_READ_SERIAL(-5),
    QF_ERR_READ_SERIAL_TIMEOUT(-6),
    QF_ERR_CHECKSUM_ERROR(-7),
    QF_ERR_CANNOT_SET_TIMEOUT(-8),

    // generic command error code
    QF_ERR_FAILED(-100),
    QF_ERR_SCAN_FAIL(-101),
    QF_ERR_NOT_FOUND(-102),
    QF_ERR_NOT_MATCH(-103),
    QF_ERR_TRY_AGAIN(-104),
    QF_ERR_TIME_OUT(-105),
    QF_ERR_MEM_FULL(-106),
    QF_ERR_EXIST_ID(-107),
    QF_ERR_FACE_TEMPLATE_LIMIT(-108),
    QF_ERR_UNSUPPORTED(-109),
    QF_ERR_INVALID_ID(-110),
    QF_ERR_TIMEOUT_MATCH(-111),
    QF_ERR_BUSY(-112),
    QF_ERR_CANCELED(-113),
    QF_ERR_DATA_ERROR(-114),
    QF_ERR_EXIST_FACE(-115),
    QF_ERR_FAKE_DETECTED(-122),

    QF_ERR_OUT_OF_MEMORY(-200),
    QF_ERR_INVALID_PARAMETER(-201),
    QF_ERR_FILE_IO(-202),
    QF_ERR_INVALID_FILE(-203),
    QF_ERR_INVALID_FIRMWARE(-204),

    // socket error
    QF_ERR_CANNOT_START_SOCKET(-301),
    QF_ERR_CANNOT_OPEN_SOCKET(-302),
    QF_ERR_CANNOT_CONNECT_SOCKET(-303),
    QF_ERR_CANNOT_READ_SOCKET(-304),
    QF_ERR_READ_SOCKET_TIMEOUT(-305),
    QF_ERR_CANNOT_WRITE_SOCKET(-306),
    QF_ERR_WRITE_SOCKET_TIMEOUT(-307),

    QF_ERR_RECOVERY_MODE(-401),
    QF_ERR_NO_SERIAL_NUMBER(-402),
    QF_ERR_INVALID_DATABASE_FORMAT(-403),
	QF_ERR_WRONG_IMAGE_FORMAT(-404),
	QF_ERR_WRONG_IMAGE_SIZE(-405),
	QF_ERR_SECURE_CODE_VERIFICATION_FAIL(-501),

    QF_ERR_UNKNOWN(-9999),

    ;
    private int value;

    QF_RET_CODE(int i) {
        this.value = i;
    }

    public int getValue() {
        return this.value;
    }

    public static QF_RET_CODE ToRetCode(int i) {
        QF_RET_CODE result = null;

        for(QF_RET_CODE iter : QF_RET_CODE.values()) {
            if(i == iter.getValue()) {
                result = iter;
            }
        }
        return result;
    }

}

