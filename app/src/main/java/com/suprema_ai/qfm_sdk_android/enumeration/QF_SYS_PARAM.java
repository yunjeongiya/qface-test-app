/*
 * Copyright (c) 2001 - 2019. Suprema Inc. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for details.
 */

package com.suprema_ai.qfm_sdk_android.enumeration;

/**
 * Please refer to the QFM Protocol Manual for the details.
 */
public enum QF_SYS_PARAM {
    /*! Timeout */
    QF_SYS_TIMEOUT(0x62),

    /*! Template size */
    QF_SYS_TEMPLATE_SIZE(0x64),

    /*! Enrollment mode */
    QF_SYS_ENROLL_MODE(0x65),

    /*! Security level */
    QF_SYS_SECURITY_LEVEL(0x66),

    /*! Encryption mode */
    QF_SYS_ENCRYPTION_MODE(0x67),

    /*! Firmware version */
    QF_SYS_FIRMWARE_VERSION(0x6e),

    /*! Serial number */
    QF_SYS_SERIAL_NUMBER(0x6f),

    /*! Baudrate */
    QF_SYS_BAUDRATE(0x71),

    /*! Number of enrolled templates */
    QF_SYS_ENROLLED_TEMPLATES(0x73),

    /*! Number of available templates */
    QF_SYS_AVAILABLE_TEMPLATES(0x74),

    /*! Scan success */
    QF_SYS_SEND_SCAN_SUCCESS(0x75),

    /*! ASCII packet */
    QF_SYS_ASCII_PACKET(0x76),

    /*! Rotate image */
    QF_SYS_ROTATE_IMAGE(0x77),

    /*! Sensitivity (Deprecated since SDK v0.3.1) */
    QF_SYS_SENSITIVITY(0x80),

    /*! Horizontal Sensitivity */
    QF_SYS_HORIZONTAL_SENSITIVITY(0x80),

    /*! Image quality */
    QF_SYS_IMAGE_QUALITY(0x81),

    /*! Auto response */
    QF_SYS_AUTO_RESPONSE(0x82),

    /*! Free scan mode */
    QF_SYS_FREE_SCAN(0x84),

    /*! Provisional enrollment */
    QF_SYS_PROVISIONAL_ENROLL(0x85),

    /*! Response delay */
    QF_SYS_RESPONSE_DELAY(0x87),

    /*! Matching timeout */
    QF_SYS_MATCHING_TIMEOUT(0x88),

    /*! Build number */
    QF_SYS_BUILD_NUMBER(0x89),

    /*! Lighing Condition */
    QF_SYS_LIGHTING_CONDITION(0x90),

    /*! Freescan delay */
    QF_SYS_FREESCAN_DELAY(0x91),

    /*! Template type */
    QF_SYS_TEMPLATE_TYPE(0x96),

    /*! Fake detection */
    QF_SYS_FAKE_DETECTION(0x98),

    /*! Protocol interface */
    QF_SYS_PROTOCOL_INTERFACE(0X9e),

    /*! Kernel version */
    QF_SYS_KERNEL_VERSION(0xa3),

    /*! Packet security */
    QF_SYS_PACKET_SECURITY(0xa5),

    /*! Mask check level */
    QF_SYS_MASK_CHECK_LEVEL(0xb1),

    /*! User feedback */
    QF_SYS_USER_FEEDBACK(0xb2),

    /*! Vertical sensitivity */
    QF_SYS_VERTICAL_SENSITIVITY(0xb3),

    /*! QFace engine version */
    QF_SYS_QFACE_ENGINE_VERSION(0xb4),

    /*! Patch version */
    QF_SYS_PATCH_VERSION(0xb5),

    /*! Enrollment restriction */
    QF_SYS_ENROLLMENT_RESTRICTION(0xb6),

    /*! Number of user */
    QF_SYS_NUMBER_OF_USER(0xb7),

    /*! User Detection */
    QF_SYS_USER_DETECTION(0xb8),

    /*! Screen Orientation */
    QF_SYS_SCREEN_ORIENTATION(0xb9),

    QF_SYS_RESERVED(0xff),
    ;
    private int value;

    QF_SYS_PARAM(int i) {
        this.value = i;
    }

    public int getValue() {
        return this.value;
    }

}
