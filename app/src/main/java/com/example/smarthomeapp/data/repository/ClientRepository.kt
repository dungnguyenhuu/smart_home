package com.example.smarthomeapp.data.repository

import androidx.annotation.NonNull
import com.example.smarthomeapp.base.network.Retrofits
import com.example.smarthomeapp.data.pojo.Response
import com.example.smarthomeapp.data.pojo.authentication.LoginRequest
import com.example.smarthomeapp.data.pojo.authentication.LoginResponse
import com.example.smarthomeapp.data.pojo.authentication.RegisterRequest
import com.example.smarthomeapp.data.pojo.device.*
import com.example.smarthomeapp.data.pojo.room.GetAllRoomsResponse
import com.example.smarthomeapp.data.pojo.room.GetDevicesInRoomResponse
import com.example.smarthomeapp.data.pojo.sensor.GetSensorRequest
import com.example.smarthomeapp.data.pojo.sensor.GetSensorResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ClientRepository {

    fun login(@NonNull @Body request: LoginRequest): Single<LoginResponse>

    fun register(@NonNull @Body request: RegisterRequest): Single<Response>

    fun getAllRooms(): Single<GetAllRoomsResponse>

    fun getDevicesInRoom(id: Int): Single<GetDevicesInRoomResponse>

    fun getAllDevices(): Single<GetAllDevicesResponse>

    fun postNewDevices(
        @NonNull @Body request: NewDeviceRequest
    ): Single<PostNewDevicesResponse>

    fun updateDeviceStatus(
        id: String,
        @NonNull @Body request: UpdateDeviceStatusRequest
    ): Single<UpdateDeviceStatusResponse>

    fun getSensor(request: GetSensorRequest): Single<GetSensorResponse>
}