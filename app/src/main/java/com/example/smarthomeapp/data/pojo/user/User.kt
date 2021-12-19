package com.example.smarthomeapp.data.pojo.user

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class User(
    @SerializedName("email") @Expose val email: String,
    @SerializedName("password") @Expose val password: String,
    @SerializedName("name") @Expose val name: String,
    @SerializedName("role") @Expose val role: String,
    @SerializedName("createdDate") @Expose val createdDate: String,
    @SerializedName("modifiedDate") @Expose val modifiedDate: String
) : Serializable