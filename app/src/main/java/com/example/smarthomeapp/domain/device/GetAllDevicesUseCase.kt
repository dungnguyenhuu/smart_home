package com.example.smarthomeapp.domain.device

import com.example.smarthomeapp.base.domain.usecase.SingleUseCase
import com.example.smarthomeapp.data.pojo.device.GetAllDevicesRequest
import com.example.smarthomeapp.data.pojo.device.GetAllDevicesResponse
import com.example.smarthomeapp.data.repository.ClientRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetAllDevicesUseCase @Inject constructor() :
    SingleUseCase<GetAllDevicesResponse, GetAllDevicesRequest>() {

    @Inject
    lateinit var repository: ClientRepository

    override fun create(params: GetAllDevicesRequest?): Single<out GetAllDevicesResponse> {
        return params?.let {
            Single.defer {
                repository.getAllDevices()
            }
        } ?: errorParamsSingle()
    }

}