/*
 * Copyright (c) 2001 - 2019. Suprema Inc. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for details.
 */

package com.suprema_ai.qfm_sdk_android.enumeration;

/**
 * Please refer to the QFM Protocol Manual for the details.
 */
public enum QF_MODULE_TYPE {
    QF_MODULE_PRO(0),
    QF_MODULE_UNKNOWN(-1),
    ;
    private int value;

    QF_MODULE_TYPE(int i) {
        this.value = i;
    }

    public int getValue() {
        return this.value;
    }

    private void setValue(int i) {
        this.value = i;
    }

    public static QF_MODULE_TYPE ToObjectType(int i) {
        QF_MODULE_TYPE result = null;

        for (QF_MODULE_TYPE iter : QF_MODULE_TYPE.values()) {
            if (i == iter.getValue()) {
                result = iter;
            }
        }
        return result;
    }


}
