package com.example.smarthomeapp.domain.device

import com.example.smarthomeapp.base.domain.usecase.SingleUseCase
import com.example.smarthomeapp.data.pojo.device.NewDeviceRequest
import com.example.smarthomeapp.data.pojo.device.PostNewDevicesResponse
import com.example.smarthomeapp.data.pojo.device.UpdateDeviceStatusRequest
import com.example.smarthomeapp.data.pojo.device.UpdateDeviceStatusResponse
import com.example.smarthomeapp.data.pojo.room.GetAllRoomsRequest
import com.example.smarthomeapp.data.repository.ClientRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class PostNewDeviceUseCase @Inject constructor() :
    SingleUseCase<PostNewDevicesResponse, NewDeviceRequest>() {

    @Inject
    lateinit var repository: ClientRepository

    override fun create(params: NewDeviceRequest?): Single<out PostNewDevicesResponse> {
        return params?.let {
            Single.defer {
                repository.postNewDevices(it)
            }
        } ?: errorParamsSingle()
    }

}