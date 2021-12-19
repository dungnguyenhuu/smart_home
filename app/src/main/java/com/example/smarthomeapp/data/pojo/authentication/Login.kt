package com.example.smarthomeapp.data.pojo.authentication

import com.example.smarthomeapp.data.pojo.Response
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("email") @Expose val email: String,
    @SerializedName("password") @Expose val password: String
)

data class LoginResponse(
    @SerializedName("accessToken") @Expose val accessToken: String
) : Response()