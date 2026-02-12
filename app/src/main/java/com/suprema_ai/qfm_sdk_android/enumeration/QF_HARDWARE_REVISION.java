/*
 * Copyright (c) 2001 - 2019. Suprema Inc. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for details.
 */

package com.suprema_ai.qfm_sdk_android.enumeration;

/**
 * Please refer to the QFM Protocol Manual for the details.
 */
public enum QF_HARDWARE_REVISION {
    QF_HARDWARE_REVISION_A(0),
    QF_SENSOR_UNKNOWN(-1),
    ;
    private int value;

    QF_HARDWARE_REVISION(int i) {
        this.value = i;
    }

    public int getValue() {
        return this.value;
    }

    public static QF_HARDWARE_REVISION ToObjectType(int i) {
        QF_HARDWARE_REVISION result = null;

        for (QF_HARDWARE_REVISION iter : QF_HARDWARE_REVISION.values()) {
            if (i == iter.getValue()) {
                result = iter;
            }
        }
        return result;
    }

}
