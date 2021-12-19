package com.example.smarthomeapp.module.credential

import android.content.Context
import com.example.smarthomeapp.module.serializer.Serializer
import com.example.smarthomeapp.util.AppSharedPreferences
import com.example.smarthomeapp.util.save
import javax.inject.Inject

class CredentialManagerImpl @Inject constructor(context: Context, serializer: Serializer) :
    CredentialManager {

    companion object {
        internal const val KEY_AUTH_TOKEN = "auth_token"
    }

    private val preferences by lazy {
        AppSharedPreferences(context, "CredentialManager", serializer)
    }

    override fun getAuthToken() = preferences.getString(KEY_AUTH_TOKEN)

    override fun saveAuthToken(token: String) = preferences.save(KEY_AUTH_TOKEN, token)
}