/*
 * Copyright (c) 2001 - 2019. Suprema Inc. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for details.
 */

package com.suprema_ai.qfm_sdk_android.enumeration;

/**
 * Please refer to the QFM Protocol Manual for the details.
 */
public enum QF_ENROLL_OPTION {
    QF_ENROLL_ADD_NEW(0x71),
    QF_ENROLL_AUTO_ID(0x79),
    QF_ENROLL_CHECK_ID(0x70),
    QF_ENROLL_CHECK_FACE(0x84),
    QF_ENROLL_CHECK_FACE_AUTO_ID(0x85),
    ;
    private int value;

    QF_ENROLL_OPTION(int i) {
        this.value = i;
    }

    public int getValue() {
        return this.value;
    }
}
