package dev.hello.viper

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import com.ccpp.pgw.sdk.android.core.PGWSDK
import com.ccpp.pgw.sdk.android.enums.APIEnvironment
import dev.hello.viper.utils.AppConstant
import dev.hello.viper.utils.CommonUtils


class ViperApp : Application() {
    lateinit var mNetworkReceiver: AppNetworkReceiver

    init {
        instance = this
    }

    companion object {
        @JvmStatic
        var mActivity: Activity? = null

        private var instance: ViperApp? = null

        fun appContext(): Context {
            return instance!!.applicationContext
        }
    }


    override fun onCreate() {
        super.onCreate()

        val context: Context = appContext()

        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(p0: Activity, p1: Bundle?) {
                mNetworkReceiver = AppNetworkReceiver()
                CommonUtils.log("AppClass Created", p0.javaClass.canonicalName as String)
            }

            override fun onActivityStarted(p0: Activity) {
                mActivity = p0
                registerNetworkForBroadcastForNought()
                CommonUtils.log("AppClass Started", p0.javaClass.canonicalName as String)
            }

            override fun onActivityResumed(p0: Activity) {
                CommonUtils.log("AppClass Resume", p0.javaClass.canonicalName as String)
            }

            override fun onActivityPaused(p0: Activity) {
                CommonUtils.log("AppClass Paused", p0.javaClass.canonicalName as String)
            }

            override fun onActivityStopped(p0: Activity) {
                CommonUtils.log("AppClass Stopped", p0.javaClass.canonicalName as String)
            }

            override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
                CommonUtils.log("AppClass SaveInstance", p0.javaClass.canonicalName as String)
            }

            override fun onActivityDestroyed(p0: Activity) {
                mActivity = null
                unregisterReceiver(mNetworkReceiver)
                CommonUtils.log("AppClass Destroyed", p0.javaClass.canonicalName as String)
            }

        })

        PGWSDK.builder(context)
            .setMerchantID(AppConstant.MerchantID)
            .setAPIEnvironment(APIEnvironment.SANDBOX)
            .init()
    }


    fun registerNetworkForBroadcastForNought() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            registerReceiver(
                mNetworkReceiver,
                IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
            )
        }
    }

}