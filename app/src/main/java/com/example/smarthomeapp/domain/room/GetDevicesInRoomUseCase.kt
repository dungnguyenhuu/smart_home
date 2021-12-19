package com.example.smarthomeapp.domain.room

import com.example.smarthomeapp.base.domain.usecase.SingleUseCase
import com.example.smarthomeapp.data.pojo.room.GetDevicesInRoomResponse
import com.example.smarthomeapp.data.repository.ClientRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetDevicesInRoomUseCase @Inject constructor() :
    SingleUseCase<GetDevicesInRoomResponse, Int>() {

    @Inject
    lateinit var repository: ClientRepository

    override fun create(params: Int?): Single<out GetDevicesInRoomResponse> {
        return params?.let {
            Single.defer {
                repository.getDevicesInRoom(it)
            }
        } ?: errorParamsSingle()
    }

}