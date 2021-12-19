package com.example.smarthomeapp.data.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class Response {
    @SerializedName("code")
    @Expose
    var code: String? = null
        private set

    @SerializedName("message")
    @Expose
    var message: String? = null
        private set

    fun isSuccess(): Boolean = ApiCode.OK == code
}

object ApiCode {
    const val OK = "200"
    const val INVALID = "400"
    const val UNEXPECTED = "500"
}