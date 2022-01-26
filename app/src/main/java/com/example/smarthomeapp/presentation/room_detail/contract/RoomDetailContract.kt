package com.example.smarthomeapp.presentation.room_detail.contract

import androidx.lifecycle.MutableLiveData
import com.example.smarthomeapp.base.scene.BaseContract
import com.example.smarthomeapp.data.pojo.room.Room
import com.example.smarthomeapp.data.pojo.sensor.Sensor
import com.example.smarthomeapp.presentation.room_detail.adapter.DeviceAdapter

interface RoomDetailContract {

    interface Scene : BaseContract.Scene {

        fun navBack()

    }

    interface ViewModel : BaseContract.ViewModel<Scene> {

        fun onBack()

        fun getRoom(): MutableLiveData<Room>

        fun getAdapter(): DeviceAdapter

        fun getSensor(): MutableLiveData<Sensor>

        fun getImageResource(): MutableLiveData<Int>

        fun addDevice()
    }

}