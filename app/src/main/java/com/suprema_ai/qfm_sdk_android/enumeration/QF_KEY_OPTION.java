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
public enum QF_KEY_OPTION {
	/*! set encryption key */
	QF_KEY_OPTION_SET_ENCRYPTION_KEY(0xC1),

	/*! set initialization vector */
	QF_KEY_OPTION_SET_INITIALIZATION_VECTOR(0xC2),

	/*! set secure key */
	QF_KEY_OPTION_SET_SECURE_KEY(0xC3),

	/*! set encryption key with verification */
	QF_KEY_OPTION_SET_ENCRYPTION_KEY_WITH_VERIFICATION(0xC4),

	/*! set initialization vector with verification */
	QF_KEY_OPTION_SET_INITIALIZATION_VECTOR_WITH_VERIFICATION(0xC5),

	/*! set secure key with verification */
	QF_KEY_OPTION_SET_SECURE_KEY_WITH_VERIFICATION(0xC6),

	/*! reset initialization vector */
	QF_KEY_OPTION_RESET_INITIALIZATION_VECTOR(0xCA),

	/*! reset secure key */
	QF_KEY_OPTION_RESET_SECURE_KEY(0xCB),

	/*! reset encryption key */
	QF_KEY_OPTION_RESET_ENCRYPTION_KEY(0xCC),	

	/*! verify encryption key */
	QF_KEY_OPTION_VERIFY_ENCRYPTION_KEY(0xD4),

	/*! verify initialization vector */
	QF_KEY_OPTION_VERIFY_INITIALIZATION_VECTOR(0xD5),
	;
	
    private int value;

    QF_KEY_OPTION(int i) {
        this.value = i;
    }

    public int getValue() {
        return this.value;
    }
}
