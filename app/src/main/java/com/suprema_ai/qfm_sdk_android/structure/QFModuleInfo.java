/*
 * Copyright (c) 2001 - 2019. Suprema Inc. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for details.
 */

package com.suprema_ai.qfm_sdk_android.structure;

import com.suprema_ai.qfm_sdk_android.enumeration.QF_HARDWARE_REVISION;
import com.suprema_ai.qfm_sdk_android.enumeration.QF_MODULE_TYPE;
import com.suprema_ai.qfm_sdk_android.enumeration.QF_MODULE_VERSION;

public class QFModuleInfo {
    public QF_MODULE_TYPE _type;
    public QF_MODULE_VERSION _version;
    public QF_HARDWARE_REVISION _hw_revision;

    public QF_MODULE_TYPE type() {
        return this._type;
    }

    public QF_MODULE_VERSION version() {
        return this._version;
    }

    public QF_HARDWARE_REVISION hw_revision() {
        return this._hw_revision;
    }

    public QFModuleInfo(QF_MODULE_TYPE t, QF_MODULE_VERSION v, QF_HARDWARE_REVISION s) {
        this._type = t;
        this._version = v;
        this._hw_revision = s;
    }

    public QFModuleInfo() {

    }

    public void setInfo(QFModuleInfo info) {
        this._type = info._type;
        this._version = info._version;
        this._hw_revision = info._hw_revision;
    }
}
