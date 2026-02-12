/*
 * Copyright (c) 2001 - 2019. Suprema Inc. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for details.
 */

package com.suprema_ai.qfm_sdk_android.enumeration;

/**
 * Please refer to the QFM Protocol Manual for the details.
 */
public enum QF_MODULE_VERSION {
    QF_VERSION_0_1(0),
    QF_VERSION_1_0(10),
    QF_VERSION_UNKNOWN(-1),
    ;
    private int value;

    QF_MODULE_VERSION(int i) {
        this.value = i;
    }

    public int getValue() {
        return this.value;
    }

    public static QF_MODULE_VERSION ToObjectType(int i) {
        QF_MODULE_VERSION result = null;

        for (QF_MODULE_VERSION iter : QF_MODULE_VERSION.values()) {
            if (i == iter.getValue()) {
                result = iter;
            }
        }
        return result;
    }

}
