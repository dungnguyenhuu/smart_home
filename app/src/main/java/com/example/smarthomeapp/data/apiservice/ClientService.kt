package com.example.smarthomeapp.data.apiservice

import androidx.annotation.NonNull
import com.example.smarthomeapp.base.network.Retrofits
import com.example.smarthomeapp.data.pojo.Response
import com.example.smarthomeapp.data.pojo.authentication.LoginRequest
import com.example.smarthomeapp.data.pojo.authentication.LoginResponse
import com.example.smarthomeapp.data.pojo.authentication.RegisterRequest
import com.example.smarthomeapp.data.pojo.device.*
import com.example.smarthomeapp.data.pojo.room.GetAllRoomsResponse
import com.example.smarthomeapp.data.pojo.room.GetDevicesInRoomResponse
import com.example.smarthomeapp.data.pojo.sensor.GetSensorResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface ClientService {

    @POST("/api/auth/login")
    @Headers(Retrofits.HEADER_NO_AUTH)
    fun login(@NonNull @Body request: LoginRequest): Single<LoginResponse>

    @POST("/api/auth/register")
    @Headers(Retrofits.HEADER_NO_AUTH)
    fun register(@NonNull @Body request: RegisterRequest): Single<Response>

    @GET("/rooms")
    fun getAllRooms(): Single<GetAllRoomsResponse>

    @GET("/rooms/{id}")
    fun getDevicesInRoom(@Path("id") id: Int): Single<GetDevicesInRoomResponse>

    @PATCH("/rooms/{id}")
    @Headers(Retrofits.HEADER_NO_AUTH)
    fun turnOffAllDevices(
        @Path("id") id: Int,
        @NonNull @Body request: UpdateDeviceStatusRequest
    ): Single<GetDevicesInRoomResponse>

    @GET("/devices")
    fun getAllDevices(): Single<GetAllDevicesResponse>

    @POST("/devices")
    @Headers(Retrofits.HEADER_NO_AUTH)
    fun postNewDevices(
        @NonNull @Body request: NewDeviceRequest
    ): Single<PostNewDevicesResponse>

    @PATCH("/devices/{id}")
    @Headers(Retrofits.HEADER_NO_AUTH)
    fun updateDeviceStatus(
        @Path("id") id: String,
        @NonNull @Body request: UpdateDeviceStatusRequest
    ): Single<UpdateDeviceStatusResponse>

    @GET("/api/sensor")
    fun getSensor(
        @Query("begin") begin: Long,
        @Query("end") end: Long
    ): Single<GetSensorResponse>

}