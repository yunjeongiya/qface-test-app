/*
 * Copyright (c) 2001 - 2019. Suprema Inc. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for details.
 */

package com.suprema_ai.qfm_sdk_android.enumeration;

/**
 * This enumeration can be used in callback functions to know what the error or the flag means to
 * be returned.
 */
public enum QF_PROTOCOL_RET_CODE {
    QF_PROTO_RET_FAILED(0x60),
    QF_PROTO_RET_SUCCESS(0x61),
    QF_PROTO_RET_SCAN_SUCCESS(0x62),
    QF_PROTO_RET_SCAN_FAIL(0x63),
    QF_PROTO_RET_NOT_FOUND(0x69),
    QF_PROTO_RET_NOT_MATCH(0x6A),
    QF_PROTO_RET_TRY_AGAIN(0x6B),
    QF_PROTO_RET_TIME_OUT(0x6C),
    QF_PROTO_RET_MEM_FULL(0x6D),
    QF_PROTO_RET_EXIST_ID(0x6E),
    QF_PROTO_RET_FACE_TEMPLATE_LIMIT(0x72),
    QF_PROTO_RET_CONTINUE(0x74),
    QF_PROTO_RET_UNSUPPORTED(0x75),
    QF_PROTO_RET_INVALID_ID(0x76),
    QF_PROTO_RET_TIMEOUT_MATCH(0x7A),
    QF_PROTO_RET_INVALID_FIRMWARE(0x7C),
    QF_PROTO_RET_BUSY(0x80),
    QF_PROTO_RET_CANCELED(0x81),
    QF_PROTO_RET_DATA_ERROR(0x82),
    QF_PROTO_RET_DATA_OK(0x83),
    QF_PROTO_RET_EXIST_FACE(0x86),
    QF_PROTO_RET_NO_SERIAL_NUMBER(0xA2),
    QF_PROTO_FAKE_DETECTED(0xB0),
    QF_PROTO_RET_INVALID_DATABASE_FORMAT(0xB2),
    QF_PROTO_RET_WRONG_IMAGE_FORMAT(0xB3),
    QF_PROTO_RET_WRONG_IMAGE_SIZE(0xB4),
    QF_PROTO_RET_USER_FEEDBACK(0xB5),
    ;
    private int value;

    QF_PROTOCOL_RET_CODE(int i) {
        this.value = i;
    }

    public int getValue() {
        return this.value;
    }

    public static QF_PROTOCOL_RET_CODE ToProtoRetCode(int i) {
        QF_PROTOCOL_RET_CODE result = null;

        for (QF_PROTOCOL_RET_CODE iter : QF_PROTOCOL_RET_CODE.values()) {
            if (i == iter.getValue()) {
                result = iter;
            }
        }
        return result;
    }
}
