package com.example.smarthomeapp.module.restful

import android.content.Context
import com.example.smarthomeapp.base.network.ConnectivityInterceptor
import com.example.smarthomeapp.base.network.Retrofits
import com.example.smarthomeapp.data.apiservice.ClientService
import com.example.smarthomeapp.data.const.Define
import com.example.smarthomeapp.module.credential.CredentialManager
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import retrofit2.create
import javax.inject.Singleton

@Module
class RestfulApiModule {

    @Singleton
    @Provides
    fun provideClientService(
        context: Context,
        gson: Gson,
        credentialManager: CredentialManager
    ): ClientService =
        Retrofits.newClient(
            Define.BASE_URL,
            gson,
            ConnectivityInterceptor(context),
            AuthenticationInterceptor(credentialManager)
        ).create()
}