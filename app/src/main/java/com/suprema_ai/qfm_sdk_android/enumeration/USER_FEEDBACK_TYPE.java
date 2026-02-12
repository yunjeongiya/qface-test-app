/*
 * Copyright (c) 2001 - 2019. Suprema Inc. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for details.
 */

package com.suprema_ai.qfm_sdk_android.enumeration;

/**
 * Please refer to the QFM Protocol Manual for the details.
 */

public enum USER_FEEDBACK_TYPE {
    USER_FEEDBACK_TYPE_MESSAGE_CODE(0x1),
    USER_FEEDBACK_TYPE_HEAD_POSITION(0x2),
    ;
    private int value;

    USER_FEEDBACK_TYPE(int i) {
        this.value = i;
    }

    public int getValue() {
        return this.value;
    }
}
