package com.example.smarthomeapp.module.restful

import android.annotation.SuppressLint
import com.example.smarthomeapp.base.network.BearerAuthenticationInterceptor
import com.example.smarthomeapp.module.credential.CredentialManager

@SuppressLint("HardwareIds")
class AuthenticationInterceptor(private val credentialManager: CredentialManager) :
    BearerAuthenticationInterceptor() {

    /*val deviceId by lazy {
        Settings.Secure.getString(
            BaseApplication.getInstance().contentResolver,
            Settings.Secure.ANDROID_ID
        )
    }*/

    override fun provideAuthenticationToken() = credentialManager.getAuthToken()

    /*override fun onIntercept(builder: Request.Builder) {
        super.onIntercept(builder)
        builder.apply {
            header("X-CmdId", deviceId)
            header("X-SentTime", System.currentTimeMillis().toString())
        }
    }*/
}