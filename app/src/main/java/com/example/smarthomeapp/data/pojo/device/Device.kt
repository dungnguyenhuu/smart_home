package com.example.smarthomeapp.data.pojo.device

import com.example.smarthomeapp.data.pojo.Response
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

data class Device(
    @SerializedName("_id") @Expose val db_id: String,
    @SerializedName("type") @Expose val type: Int,
    @SerializedName("room_id") @Expose val roomId: String,
    @SerializedName("name") @Expose val name: String,
    @SerializedName("status") @Expose val status: String,
    @SerializedName("mode") @Expose val mode: String,
    @SerializedName("remote") @Expose val remote: Boolean,
    @SerializedName("position") @Expose val position: Int


) : Serializable

enum class STATUS(var value: String) {
    ON("on"), OFF("off")
}

enum class MODE(var value: String) {
    AUTO("AUTO"), MANUAL("MANUAL")
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

data class UpdateDeviceModeRequest(
    @SerializedName("mode") @Expose val mode: String
)

data class UpdateDeviceModeResponse(
    val device: Device
) : Response()

data class NewDeviceRequest(
    @SerializedName("type") @Expose val type: Int,
    @SerializedName("position") @Expose val position: Int,
    @SerializedName("room_id") @Expose val roomId: Int,
    @SerializedName("name") @Expose val name: String,
    @SerializedName("status") @Expose val status: String
)

data class PostNewDevicesResponse(
    val device: Device
) : Response()
