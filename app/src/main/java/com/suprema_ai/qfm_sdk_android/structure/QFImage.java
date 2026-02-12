/*
 * Copyright (c) 2001 - 2019. Suprema Inc. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for details.
 */

package com.suprema_ai.qfm_sdk_android.structure;

public class QFImage {
    private int _length;
    private byte[] _data = new byte[1280*720*3];

    public int length() {
        return this._length;
    }

    public byte[] data() {
        return this._data;
    }

    public QFImage(int length, byte[] data) {
        this._length = length;
        this._data = data;
    }

    public QFImage() {

    }
}

