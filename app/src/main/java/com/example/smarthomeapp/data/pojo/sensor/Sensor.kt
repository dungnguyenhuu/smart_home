package com.example.smarthomeapp.data.pojo.sensor

import com.example.smarthomeapp.data.pojo.Response
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Sensor(
    @SerializedName("_id") @Expose val id: String,
    @SerializedName("humidityAir") @Expose val humidityAir: Double,
    @SerializedName("temperature") @Expose val temperature: Double,
    @SerializedName("createdDate") @Expose val createdDate: String,
    @SerializedName("modifiedDate") @Expose val modifiedDate: String,
    @SerializedName("__v") @Expose val __v: Int
) : Serializable

data class GetSensorRequest(
    @SerializedName("begin") @Expose val begin: Long,
    @SerializedName("end") @Expose val end: Long
)

data class GetSensorResponse(
    @SerializedName("sensor") @Expose val sensor: Sensor
) : Response()