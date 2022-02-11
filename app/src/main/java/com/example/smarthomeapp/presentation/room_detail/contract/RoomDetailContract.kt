package com.example.smarthomeapp.presentation.room_detail.contract

import androidx.lifecycle.MutableLiveData
import com.example.smarthomeapp.base.scene.BaseContract
import com.example.smarthomeapp.data.pojo.device.Device
import com.example.smarthomeapp.data.pojo.device.UpdateDeviceStatusRequest
import com.example.smarthomeapp.data.pojo.room.Room
import com.example.smarthomeapp.data.pojo.sensor.Sensor
import com.example.smarthomeapp.presentation.room_detail.adapter.DeviceAdapter

interface RoomDetailContract {

    interface Scene : BaseContract.Scene {

        fun navBack()

        fun onNavigate(room: Room)

    }

    interface ViewModel : BaseContract.ViewModel<Scene> {

        fun onBack()

        fun getRoom(): MutableLiveData<Room>

        fun getAdapter(): DeviceAdapter

        fun getSensor(): MutableLiveData<Sensor>

        fun getImageResource(): MutableLiveData<Int>

        fun addDevice()

        fun onTurnOffAllDevices()

        fun getIcon(): MutableLiveData<ArrayList<Int>>

        fun getIdDevice(position : Int): String

        fun updateDeviceStatus(pair: Pair<String, UpdateDeviceStatusRequest>)

        fun getNumberDevice(): MutableLiveData<Int>

        fun getListDevice(): ArrayList<Device>

    }
}