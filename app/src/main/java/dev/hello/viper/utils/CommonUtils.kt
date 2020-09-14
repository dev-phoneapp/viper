package dev.hello.viper.utils

import android.util.Log
import dev.hello.viper.BuildConfig

class CommonUtils {
    companion object {
        fun log(tag: String, msg: String) {
            if (BuildConfig.DEBUG) {
                Log.e(tag, msg)
            }
        }
    }
}