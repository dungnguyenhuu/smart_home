package com.example.smarthomeapp.data.pojo.room

import com.example.smarthomeapp.data.pojo.Response
import com.example.smarthomeapp.data.pojo.device.Device
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

data class Room(
    @SerializedName("id") @Expose val id: Int,
    @SerializedName("name") @Expose val name: String
) : Serializable

open class GetAllRoomsRequest

data class GetAllRoomsResponse(
    @SerializedName("rooms") @Expose val rooms: List<Room>
) : Response()

data class GetDevicesInRoomResponse(
    @SerializedName("device") @Expose val devices: List<Device>
) : Response()