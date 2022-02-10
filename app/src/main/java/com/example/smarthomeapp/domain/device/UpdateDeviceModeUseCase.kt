package com.example.smarthomeapp.domain.device

import com.example.smarthomeapp.base.domain.usecase.SingleUseCase
import com.example.smarthomeapp.data.pojo.device.UpdateDeviceModeRequest
import com.example.smarthomeapp.data.pojo.device.UpdateDeviceModeResponse
import com.example.smarthomeapp.data.repository.ClientRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class UpdateDeviceModeUseCase @Inject constructor() :
    SingleUseCase<UpdateDeviceModeResponse, Pair<String, UpdateDeviceModeRequest>>() {

    @Inject
    lateinit var repository: ClientRepository

    override fun create(params: Pair<String, UpdateDeviceModeRequest>?): Single<out UpdateDeviceModeResponse> {
        return params?.let {
            Single.defer {
                repository.updateDeviceMode(it.first, it.second)
            }
        } ?: errorParamsSingle()
    }

}