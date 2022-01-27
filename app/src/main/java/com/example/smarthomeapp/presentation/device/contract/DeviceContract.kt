package com.example.smarthomeapp.presentation.device.contract

import androidx.lifecycle.MutableLiveData
import com.example.smarthomeapp.base.scene.BaseContract
import com.example.smarthomeapp.data.pojo.room.Room

interface DeviceContract {
    interface Scene : BaseContract.Scene {

        fun navBack()

    }

    interface ViewModel : BaseContract.ViewModel<Scene> {

        fun onBack()

        fun getRoom(): MutableLiveData<Room>

        fun getDeviceType(): MutableLiveData<Int>

        fun getDeviceName(): MutableLiveData<String>

        fun addDevice()
    }

}