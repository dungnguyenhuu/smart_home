package com.example.smarthomeapp.domain.room

import com.example.smarthomeapp.base.domain.usecase.SingleUseCase
import com.example.smarthomeapp.data.pojo.room.GetAllRoomsRequest
import com.example.smarthomeapp.data.pojo.room.GetAllRoomsResponse
import com.example.smarthomeapp.data.repository.ClientRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetAllRoomsUseCase @Inject constructor() :
    SingleUseCase<GetAllRoomsResponse, GetAllRoomsRequest>() {

    @Inject
    lateinit var repository: ClientRepository

    override fun create(params: GetAllRoomsRequest?): Single<out GetAllRoomsResponse> {
        return params?.let {
            Single.defer {
                repository.getAllRooms()
            }
        } ?: errorParamsSingle()
    }

}