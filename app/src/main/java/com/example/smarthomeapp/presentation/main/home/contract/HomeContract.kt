package com.example.smarthomeapp.presentation.main.home.contract

import com.example.smarthomeapp.base.scene.BaseContract
import com.example.smarthomeapp.data.pojo.room.Room
import com.example.smarthomeapp.presentation.main.home.adapter.RoomAdapter

interface HomeContract {

    interface Scene : BaseContract.Scene {

        fun onNavigate(room: Room)

    }

    interface ViewModel : BaseContract.ViewModel<Scene> {

        fun getAdapter(): RoomAdapter

    }

}