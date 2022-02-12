
package com.example.smarthomeapp.presentation.device.contract

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.R
import com.example.smarthomeapp.base.annotation.LoadingType
import com.example.smarthomeapp.base.viewmodel.AndroidIteratorViewModel
import com.example.smarthomeapp.data.pojo.device.NewDeviceRequest
import com.example.smarthomeapp.data.pojo.device.PostNewDevicesResponse
import com.example.smarthomeapp.data.pojo.device.STATUS
import com.example.smarthomeapp.data.pojo.room.Room
import com.example.smarthomeapp.domain.device.PostNewDeviceUseCase
import com.example.smarthomeapp.presentation.device.POSITION
import com.example.smarthomeapp.presentation.room_detail.ROOM
import timber.log.Timber
import javax.inject.Inject

class DeviceViewModel @Inject constructor(application: Application) :
    AndroidIteratorViewModel<DeviceContract.Scene>(application), DeviceContract.ViewModel,
    LifecycleObserver {

    @Inject
    lateinit var postNewDeviceUseCase: PostNewDeviceUseCase

    private var livePosition = MutableLiveData<Int>()
    private var liveRoom = MutableLiveData<Room>()
    private var deviceType = MutableLiveData<Int>()
    private var deviceName = MutableLiveData<String>()

    override fun onViewModelCreated() {
        super.onViewModelCreated()
        val room: Room = arguments?.getSerializable(ROOM) as Room
        livePosition.value = arguments?.getSerializable(POSITION) as Int?
        liveRoom.value = room
        deviceType.value = 0

    }

    override fun onAttachLifecycle(owner: LifecycleOwner) {
        super.onAttachLifecycle(owner)
        owner.lifecycle.addObserver(this)
    }


    override fun onBack() {
        scene?.navBack()
    }

    override fun getRoom() = liveRoom

    override fun getDeviceType() = deviceType

    override fun getDeviceName() = deviceName

    override fun addDevice() {
        val deviceRequest = NewDeviceRequest(livePosition.value!!, deviceType.value!!, liveRoom.value?.id!!, "${deviceName.value}", STATUS.OFF.value)
        fetch(
            postNewDeviceUseCase,
            object : ApiCallback<PostNewDevicesResponse>(LoadingType.BLOCKING) {
                override fun onApiResponseSuccess(response: PostNewDevicesResponse) {
                    Timber.d(response.message)
                    Toast.makeText(getApplication(), "Add device successfully", Toast.LENGTH_SHORT).show()
                    scene?.navBack()
                }
            },deviceRequest
        )
    }


}