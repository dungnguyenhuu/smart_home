package com.example.smarthomeapp.base.network

import com.example.smarthomeapp.base.scene.BaseApplication
import com.google.gson.Gson
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object Retrofits {

    const val RESTFUL_NO_AUTH = "No-Authentication"
    const val HEADER_NO_AUTH = "$RESTFUL_NO_AUTH:true"

    private fun defaultOkHttpClient(vararg interceptors: Interceptor): OkHttpClient {
        return OkHttpClient.Builder().apply {
            this.interceptors().addAll(interceptors)
            BaseApplication.getInstance().let { it ->
                HttpLoggingInterceptor().apply {
                    level = if (it.isDebugMode()) HttpLoggingInterceptor.Level.BODY
                    else HttpLoggingInterceptor.Level.NONE
                }.also { this.interceptors().add(it) }
                Cache(it.cacheDir, 10 * 1024 * 1024).also {
                    this.cache(it)
                }
            }
            readTimeout(30, TimeUnit.SECONDS)
            connectTimeout(30, TimeUnit.SECONDS)
            retryOnConnectionFailure(true)
        }.build()
    }

    fun newClient(domain: String, gson: Gson, vararg interceptors: Interceptor): Retrofit {
        return Retrofit.Builder()
            .baseUrl(domain)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(defaultOkHttpClient(*interceptors))
            .build()
    }
}