/*
 * Copyright (c) 2001 - 2019. Suprema Inc. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for details.
 */

package com.suprema_ai.qfm_sdk_android.enumeration;

/**
 * Please refer to the QFM Protocol Manual for the details.
 */
public enum QF_USER_FEEDBACK {
    QF_USER_FEEDBACK_FACE_DETECTED(0x0),
    QF_USER_FEEDBACK_LOOK_AT_THE_CAMERA_CORRECTLY(0x1),
    QF_USER_FEEDBACK_TURN_YOUR_HEAD_RIGHT(0x2),
    QF_USER_FEEDBACK_TURN_YOUR_HEAD_LEFT(0x3),
    QF_USER_FEEDBACK_TURN_YOUR_HEAD_UP(0x4),
    QF_USER_FEEDBACK_TURN_YOUR_HEAD_DOWN(0x5),
    QF_USER_FEEDBACK_MOVE_FORWARD(0x6),
    QF_USER_FEEDBACK_MOVE_BACKWARD(0x7),
    // 0x8 and 0x9 are reserved
    QF_USER_FEEDBACK_OUT_OF_ENROLLMENT_AREA(0xA),
    // 0xB ~ 0xFE are reserved
    QF_USER_FEEDBACK_FACE_NOT_DETECTED(0xFF),
    ;
    private int value;

    QF_USER_FEEDBACK(int i) {
        this.value = i;
    }

    public int getValue() {
        return this.value;
    }

    public static QF_USER_FEEDBACK ToUserFeedback(int i) {
        QF_USER_FEEDBACK userfeedback = null;

        for (QF_USER_FEEDBACK iter : QF_USER_FEEDBACK.values()) {
            if (i == iter.getValue()) {
                userfeedback = iter;
            }
        }
        return userfeedback;
    }
}
