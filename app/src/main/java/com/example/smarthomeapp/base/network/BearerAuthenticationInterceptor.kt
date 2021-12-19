package com.example.smarthomeapp.base.network

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

abstract class BearerAuthenticationInterceptor : Interceptor {

    final override fun intercept(chain: Interceptor.Chain): Response {
        return chain.run {
            request().let { origin ->
                origin.newBuilder().apply {
                    onIntercept(this)
                    origin.headers[Retrofits.RESTFUL_NO_AUTH].let {
                        if ("true" != it) {
                            provideAuthenticationToken()?.let { token ->
                                header("Authorization", "Bearer $token")
                            }
                        }
                    }
                }
            }.let {
                proceed(it.build())
            }
        }
    }

    abstract fun provideAuthenticationToken(): String?

    open fun onIntercept(builder: Request.Builder) {

    }
}