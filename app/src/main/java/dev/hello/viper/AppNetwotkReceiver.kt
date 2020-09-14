package dev.hello.viper

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.util.Log
import dev.hello.viper.utils.CommonUtils
import java.lang.Exception

class AppNetworkReceiver : BroadcastReceiver() {
    val TAG = "ViperAppNetworkRceiver"

    companion object {
        var isActive = false
    }

    override fun onReceive(p0: Context?, p1: Intent?) {
        isActive = isOnline(p0!!)
        var mAct: Activity = ViperApp.mActivity!!

        if (!isActive) {
            CommonUtils.log(TAG, "App Disconnect")
        } else {
            CommonUtils.log(TAG, "App Connect")
        }
    }

    private fun isOnline(mCtx: Context): Boolean {
        return try {
            val cm: ConnectivityManager =
                mCtx.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = cm.activeNetworkInfo
            (netInfo != null && netInfo.isConnected)

        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

}
