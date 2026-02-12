package com.suprema_ai.qfm_sdk_android.message_handler;

import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import java.lang.ref.WeakReference;

public class MessageHandler extends Handler {
    public final WeakReference<AppCompatActivity> mActivity;

    public MessageHandler(AppCompatActivity activity) {
        mActivity = new WeakReference<>(activity);
    }
}
