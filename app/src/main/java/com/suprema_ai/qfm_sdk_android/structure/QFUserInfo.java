/*
 * Copyright (c) 2001 - 2019. Suprema Inc. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for details.
 */

package com.suprema_ai.qfm_sdk_android.structure;

import java.util.Arrays;

public class QFUserInfo {
    private int _userID = 0;
    private byte _numOfTemplate = 0;
    private byte _reserved_0 = 0;
    private byte _reserved_1 = 0;
    private byte _reserved_2 = 0;

    public int userID() {
        return this._userID;
    }

    public byte numOfTemplate() {
        return this._numOfTemplate;
    }

    public byte reserved_0() { return this._reserved_0; }
    public byte reserved_1() { return this._reserved_1; }
    public byte reserved_2() { return this._reserved_2; }

    public QFUserInfo(int userID, byte numOfTemplate, byte reserved_0, byte reserved_1, byte reserved_2) {
        this._userID = userID;
        this._numOfTemplate = numOfTemplate;
        this._reserved_0 = reserved_0;
        this._reserved_1 = reserved_1;
        this._reserved_2 = reserved_2;
    }

    public QFUserInfo() {

    }
}
