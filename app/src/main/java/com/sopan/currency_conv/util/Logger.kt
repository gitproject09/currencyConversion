package com.ente_kada.android.view.utilis

import android.text.TextUtils
import android.util.Log
import com.sopan.currency_conv.BuildConfig

object Logger {
    var IS_DEBUG_ENABLED = false
    var IS_FILE_WRITING_ENABLED = false
    fun enable() {
        IS_DEBUG_ENABLED = true
    }


    fun i(TAG: String?, msg: String?) {
        if (BuildConfig.DEBUG && !TextUtils.isEmpty(msg) && !TextUtils.isEmpty(TAG)) {
            Log.i(TAG, msg!!)
        }
    }

    fun v(TAG: String?, msg: String?) {
        if (BuildConfig.DEBUG && !TextUtils.isEmpty(msg) && !TextUtils.isEmpty(TAG)) {
            Log.v(TAG, msg!!)
        }
    }

    fun w(TAG: String?, msg: String?) {
        if (BuildConfig.DEBUG && !TextUtils.isEmpty(msg) && !TextUtils.isEmpty(TAG)) {
            Log.w(TAG, msg!!)
        }
    }

    fun d(TAG: String?, msg: String?) {
        if (BuildConfig.DEBUG && !TextUtils.isEmpty(msg) && !TextUtils.isEmpty(TAG)) {
            Log.d(TAG, msg!!)
        }
    }

    fun e(TAG: String?, msg: String?) {
        if (BuildConfig.DEBUG && !TextUtils.isEmpty(msg) && !TextUtils.isEmpty(TAG)) {
            Log.e(TAG, msg!!)
        }
    }
}