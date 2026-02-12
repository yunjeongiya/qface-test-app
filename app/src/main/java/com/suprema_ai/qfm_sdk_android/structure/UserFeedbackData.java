/*
 * Copyright (c) 2001 - 2019. Suprema Inc. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for details.
 */

package com.suprema_ai.qfm_sdk_android.structure;

public class UserFeedbackData {
    private int _fields;
    private int _messageCode;
    private HeadPosition _headPosition;
    
    public int fields() {
        return _fields;
    }
    
    public int messageCode() {
       return _messageCode;
    }
    
    public HeadPosition headPosition() {
      return _headPosition;
    }
    
    public static class HeadPosition {
        private int _topLeftX;
        private int _topLeftY;
        private int _bottomRightX;
        private int _bottomRightY;

        public HeadPosition(int topLeftX, int topLeftY, int bottomRightX, int bottomRightY) {
            this._topLeftX = topLeftX;
            this._topLeftY = topLeftY;
            this._bottomRightX = bottomRightX;
            this._bottomRightY = bottomRightY;
        }
    }

    public int topLeftX() {
        return this._headPosition._topLeftX;
    }
    
    public int topLeftY() {
        return this._headPosition._topLeftY;
    }

    public int bottomRightX() {
        return this._headPosition._bottomRightX;
    }

    public int bottomRightY() {
        return this._headPosition._bottomRightY;
    }

    public UserFeedbackData(int fields, int messageCode, HeadPosition headPosition) {
        this._fields = fields;
        this._messageCode = messageCode;
        this._headPosition = headPosition;
    }
}
