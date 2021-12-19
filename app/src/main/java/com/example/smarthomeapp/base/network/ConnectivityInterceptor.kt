package com.example.smarthomeapp.base.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.getSystemService
import okhttp3.Interceptor
import okhttp3.Response

class ConnectivityInterceptor(context: Context) : Interceptor {

    private val networkMonitor: NetworkMonitor by lazy {
        context.getSystemService<ConnectivityManager>()?.let {
            NetworkMonitorAndroidM(it)
        } ?: object : NetworkMonitor {
            override fun isConnected() = true
        }
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        if (networkMonitor.isConnected()) {
            return chain.run {
                proceed(request().newBuilder().build())
            }
        }
        throw NoConnectivityException()
    }

    interface NetworkMonitor {

        fun isConnected(): Boolean
    }

    @RequiresApi(Build.VERSION_CODES.M)
    inner class NetworkMonitorAndroidM(private val connectivityManager: ConnectivityManager) :
        NetworkMonitor {

        override fun isConnected(): Boolean {
            return connectivityManager.run {
                activeNetwork?.let {
                    getNetworkCapabilities(it)
                }
            }?.let {
                it.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
                        || it.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                        || it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
            } ?: false
        }

    }

    @Suppress("DEPRECATION")
    inner class NetworkMonitorLegacy(private val connectivityManager: ConnectivityManager) :
        NetworkMonitor {

        override fun isConnected(): Boolean {
            return connectivityManager.activeNetworkInfo?.isConnected ?: false
        }

    }
}