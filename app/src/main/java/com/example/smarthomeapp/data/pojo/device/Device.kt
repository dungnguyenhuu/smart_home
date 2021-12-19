package com.example.smarthomeapp.data.pojo.device

import com.example.smarthomeapp.data.pojo.Response
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Device(
    @SerializedName("_id") @Expose val db_id: String,
    @SerializedName("id") @Expose val id: Int,
    @SerializedName("room_id") @Expose val roomId: String,
    @SerializedName("name") @Expose val name: String,
    @SerializedName("status") @Expose val status: String
) : Serializable

enum class STATUS(var value: String) {
    ON("on"), OFF("off")
}

open class GetAllDevicesRequest

data class GetAllDevicesResponse(
    val devices: List<Device>
) : Response()

data class UpdateDeviceStatusRequest(
    @SerializedName("status") @Expose val status: String
)

data class UpdateDeviceStatusResponse(
    val device: Device
) : Response()