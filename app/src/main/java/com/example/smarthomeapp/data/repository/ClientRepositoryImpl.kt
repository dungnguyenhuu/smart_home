package com.example.smarthomeapp.data.repository

import com.example.smarthomeapp.data.apiservice.ClientService
import com.example.smarthomeapp.data.pojo.authentication.LoginRequest
import com.example.smarthomeapp.data.pojo.authentication.RegisterRequest
import com.example.smarthomeapp.data.pojo.device.NewDeviceRequest
import com.example.smarthomeapp.data.pojo.device.UpdateDeviceStatusRequest
import com.example.smarthomeapp.data.pojo.sensor.GetSensorRequest
import javax.inject.Inject

class ClientRepositoryImpl @Inject constructor(private val clientService: ClientService) :
    ClientRepository {

    override fun login(request: LoginRequest) = clientService.login(request)

    override fun register(request: RegisterRequest) = clientService.register(request)

    override fun getAllRooms() = clientService.getAllRooms()

    override fun getDevicesInRoom(id: Int) = clientService.getDevicesInRoom(id)

    override fun turnOffAllDevices(id: Int, request: UpdateDeviceStatusRequest) =
        clientService.turnOffAllDevices(id, request)

    override fun getAllDevices() = clientService.getAllDevices()

    override fun postNewDevices(request: NewDeviceRequest) =
        clientService.postNewDevices(request)

    override fun updateDeviceStatus(id: String, request: UpdateDeviceStatusRequest) =
        clientService.updateDeviceStatus(id, request)

    override fun getSensor(request: GetSensorRequest) =
        clientService.getSensor(request.begin, request.end)

}