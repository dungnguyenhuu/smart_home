package com.example.smarthomeapp.domain.sensor

import com.example.smarthomeapp.base.domain.usecase.SingleUseCase
import com.example.smarthomeapp.data.pojo.sensor.GetSensorRequest
import com.example.smarthomeapp.data.pojo.sensor.GetSensorResponse
import com.example.smarthomeapp.data.repository.ClientRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetSensorUseCase @Inject constructor() :
    SingleUseCase<GetSensorResponse, GetSensorRequest>() {

    @Inject
    lateinit var repository: ClientRepository

    override fun create(params: GetSensorRequest?): Single<out GetSensorResponse> {
        return params?.let {
            Single.defer {
                repository.getSensor(it)
            }
        } ?: errorParamsSingle()
    }

}