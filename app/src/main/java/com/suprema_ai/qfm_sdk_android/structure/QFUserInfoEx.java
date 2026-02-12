/*
 * Copyright (c) 2001 - 2019. Suprema Inc. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for details.
 */

package com.suprema_ai.qfm_sdk_android.structure;

import java.util.Arrays;

public class QFUserInfoEx {
    private int _userID = 0;
    private int[] _checkSum = new int[20];
    private byte _numOfTemplate = 0;
    private byte _templateProperty = 0;
    private byte _encryptionMode = 0;
    private byte _subID = 0;
    private byte _mask = 0;    
    private byte _reserved_0 = 0;

    public int userID() {
        return this._userID;
    }

    public int[] checkSum() {
        return this._checkSum;
    }

    public byte numOfTemplate() {
        return this._numOfTemplate;
    }

    public byte templateProperty() {
        return this._templateProperty;
    }

    public byte encryptionMode() {
        return this._encryptionMode;
    }

    public byte subID() {
        return this._subID;
    }

    public byte mask() {
        return this._mask;
    }

    public byte reserved_0() { return this._reserved_0; }

    public QFUserInfoEx(int userID, int[] checksum, byte numOfTemplate, byte templateProperty, byte encryptionMode, byte subID, byte mask, byte reserved_0) {
        this._userID = userID;
        this._checkSum = Arrays.copyOf(checksum, checksum.length);
        this._numOfTemplate = numOfTemplate;
        this._templateProperty = templateProperty;
        this._encryptionMode = encryptionMode;
        this._subID = subID;
        this._mask = mask;
        this._reserved_0 = reserved_0;
    }

    public QFUserInfoEx() {

    }
}
