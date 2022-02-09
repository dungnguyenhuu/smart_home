package com.example.smarthomeapp.domain.room

import com.example.smarthomeapp.base.domain.usecase.SingleUseCase
import com.example.smarthomeapp.data.pojo.device.UpdateDeviceStatusRequest
import com.example.smarthomeapp.data.pojo.room.GetDevicesInRoomResponse
import com.example.smarthomeapp.data.repository.ClientRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class UpdateDevicesInRoomUseCase @Inject constructor() :
    SingleUseCase<GetDevicesInRoomResponse, Pair<Int, UpdateDeviceStatusRequest>>() {

    @Inject
    lateinit var repository: ClientRepository

    override fun create(params: Pair<Int, UpdateDeviceStatusRequest>?): Single<out GetDevicesInRoomResponse> {
        return params?.let {
            Single.defer {
                repository.turnOffAllDevices(it.first, it.second)
            }
        } ?: errorParamsSingle()
    }
}