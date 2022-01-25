package com.example.smarthomeapp.domain.device

import com.example.smarthomeapp.base.domain.usecase.SingleUseCase
import com.example.smarthomeapp.data.pojo.device.UpdateDeviceStatusRequest
import com.example.smarthomeapp.data.pojo.device.UpdateDeviceStatusResponse
import com.example.smarthomeapp.data.repository.ClientRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class UpdateDeviceStatusUseCase @Inject constructor() :
    SingleUseCase<UpdateDeviceStatusResponse, Pair<String, UpdateDeviceStatusRequest>>() {

    @Inject
    lateinit var repository: ClientRepository

    override fun create(params: Pair<String, UpdateDeviceStatusRequest>?): Single<out UpdateDeviceStatusResponse> {
        return params?.let {
            Single.defer {
                repository.updateDeviceStatus(it.first, it.second)
            }
        } ?: errorParamsSingle()
    }

}