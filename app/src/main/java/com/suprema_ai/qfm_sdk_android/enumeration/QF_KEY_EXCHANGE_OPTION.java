/*
 * Copyright (c) 2001 - 2019. Suprema Inc. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for details.
 */

package com.suprema_ai.qfm_sdk_android.enumeration;

// Command definitions
//

/**
 * Please refer to the QFM Protocol Manual for the details.
 */
public enum QF_KEY_EXCHANGE_OPTION {
	QF_KEY_EXCHANGE_OPTION_SET_PUBLIC_KEY(0xCD),
    QF_KEY_EXCHANGE_OPTION_GET_PUBLIC_KEY(0xCE),	
    ;
    private int value;

    QF_KEY_EXCHANGE_OPTION(int i) {
        this.value = i;
    }

    public int getValue() {
        return this.value;
    }
}
