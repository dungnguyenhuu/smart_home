package com.example.smarthomeapp.data.pojo.authentication

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("name") @Expose val name: String,
    @SerializedName("email") @Expose val email: String,
    @SerializedName("password") @Expose val password: String,
    @SerializedName("role") @Expose val role: String
)