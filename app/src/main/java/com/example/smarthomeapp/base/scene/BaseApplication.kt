package com.example.smarthomeapp.base.scene

import android.annotation.SuppressLint
import android.content.pm.ApplicationInfo
import android.provider.Settings
import androidx.annotation.CallSuper
import dagger.android.DaggerApplication
import timber.log.Timber

abstract class BaseApplication : DaggerApplication() {

    companion object {

        private lateinit var instance: BaseApplication

        fun getInstance() = instance

        val deviceId by lazy { generateDeviceId() }

        val deviceName by lazy { android.os.Build.MODEL }

        @SuppressLint("HardwareIds")
        private fun generateDeviceId() = Settings.Secure.getString(
            instance.contentResolver, Settings.Secure.ANDROID_ID
        )
    }

    @CallSuper
    override fun onCreate() {
        super.onCreate()
        instance = this
        Timber.plant(Timber.DebugTree())
    }

    fun isDebugMode() = (applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) != 0

}

fun appInstance() = BaseApplication.getInstance()