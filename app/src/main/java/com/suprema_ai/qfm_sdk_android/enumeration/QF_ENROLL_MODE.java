/*
 * Copyright (c) 2001 - 2019. Suprema Inc. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for details.
 */

package com.suprema_ai.qfm_sdk_android.enumeration;

/**
 * Please refer to the QFM Protocol Manual for the details.
 */
public enum QF_ENROLL_MODE {
    QF_ENROLL_ONE_TIME(0x30),
    ;
    private int value;

    QF_ENROLL_MODE(int i) {
        this.value = i;
    }

    public int getValue() {
        return this.value;
    }

    public static QF_ENROLL_MODE ToEnrollMode(int i) {
        QF_ENROLL_MODE enrollMode = null;

        for (QF_ENROLL_MODE iter : QF_ENROLL_MODE.values()) {
            if (i == iter.getValue()) {
                enrollMode = iter;
            }
        }
        return enrollMode;
    }

}
