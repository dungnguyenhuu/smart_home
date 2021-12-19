package com.example.smarthomeapp.presentation.main.home.contract

import android.app.Application
import android.view.View
import com.example.smarthomeapp.base.adapter.OnItemClick
import com.example.smarthomeapp.base.annotation.LoadingType
import com.example.smarthomeapp.base.viewmodel.AndroidIteratorViewModel
import com.example.smarthomeapp.data.pojo.room.GetAllRoomsRequest
import com.example.smarthomeapp.data.pojo.room.GetAllRoomsResponse
import com.example.smarthomeapp.data.pojo.room.Room
import com.example.smarthomeapp.domain.room.GetAllRoomsUseCase
import com.example.smarthomeapp.presentation.main.home.adapter.RoomAdapter
import javax.inject.Inject

class HomeViewModel @Inject constructor(application: Application) :
    AndroidIteratorViewModel<HomeContract.Scene>(application), HomeContract.ViewModel {

    @Inject
    lateinit var getAllRoomsUseCase: GetAllRoomsUseCase

    private var adapter = RoomAdapter().apply {
        setItemClickListener(object : OnItemClick<Room?> {
            override fun onItemClick(position: Int, view: View?, t: Room?) {
                t?.let {
                    scene?.onNavigate(it)
                }
            }
        })
    }

    override fun getAdapter() = adapter

    override fun onViewModelCreated() {
        super.onViewModelCreated()
        getAllRooms(GetAllRoomsRequest())
    }

    private fun getAllRooms(getAllRoomsRequest: GetAllRoomsRequest) {
        fetch(
            getAllRoomsUseCase,
            object : ApiCallback<GetAllRoomsResponse>(LoadingType.BLOCKING) {
                override fun onApiResponseSuccess(response: GetAllRoomsResponse) {
                    adapter.setData(response.rooms)
                }
            }, getAllRoomsRequest
        )
    }
}