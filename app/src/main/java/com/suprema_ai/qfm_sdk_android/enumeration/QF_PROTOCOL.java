/*
 * Copyright (c) 2001 - 2019. Suprema Inc. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for details.
 */

package com.suprema_ai.qfm_sdk_android.enumeration;

/**
 * Please refer to the QFM Protocol Manual for the details.
 */
public enum QF_PROTOCOL {
    QF_SINGLE_PROTOCOL(0),
    QF_NETWORK_PROTOCOL(1),
    ;
    private int value;

    QF_PROTOCOL(int i) {
        this.value = i;
    }

    public int getValue() {
        return this.value;
    }

    public static QF_PROTOCOL ToProtocol(int i) {
        QF_PROTOCOL protocol = null;

        for (QF_PROTOCOL iter : QF_PROTOCOL.values()) {
            if (i == iter.getValue()) {
                protocol = iter;
            }
        }
        return protocol;
    }
}
