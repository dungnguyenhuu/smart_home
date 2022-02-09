package com.example.smarthomeapp.presentation.room_detail.contract

import android.app.Application
import android.view.View
import android.widget.Toast
import androidx.lifecycle.*
import com.example.smarthomeapp.R
import com.example.smarthomeapp.base.adapter.OnItemClick
import com.example.smarthomeapp.base.annotation.LoadingType
import com.example.smarthomeapp.base.viewmodel.AndroidIteratorViewModel
import com.example.smarthomeapp.data.pojo.device.Device
import com.example.smarthomeapp.data.pojo.device.STATUS
import com.example.smarthomeapp.data.pojo.device.UpdateDeviceStatusRequest
import com.example.smarthomeapp.data.pojo.device.UpdateDeviceStatusResponse
import com.example.smarthomeapp.data.pojo.room.GetDevicesInRoomResponse
import com.example.smarthomeapp.data.pojo.room.Room
import com.example.smarthomeapp.data.pojo.sensor.GetSensorRequest
import com.example.smarthomeapp.data.pojo.sensor.GetSensorResponse
import com.example.smarthomeapp.data.pojo.sensor.Sensor
import com.example.smarthomeapp.domain.device.UpdateDeviceStatusUseCase
import com.example.smarthomeapp.domain.room.GetDevicesInRoomUseCase
import com.example.smarthomeapp.domain.room.UpdateDevicesInRoomUseCase
import com.example.smarthomeapp.domain.sensor.GetSensorUseCase
import com.example.smarthomeapp.presentation.room_detail.ROOM
import com.example.smarthomeapp.presentation.room_detail.adapter.DeviceAdapter
import timber.log.Timber
import javax.inject.Inject

class RoomDetailViewModel @Inject constructor(application: Application) :
    AndroidIteratorViewModel<RoomDetailContract.Scene>(application), RoomDetailContract.ViewModel,
    LifecycleObserver {

    private val roomIcons = intArrayOf(
        R.drawable.ic_living_room,
        R.drawable.ic_bathroom,
        R.drawable.ic_bedroom,
        R.drawable.ic_kitchen
    )
    @Inject
    lateinit var getDevicesInRoomUseCase: GetDevicesInRoomUseCase

    @Inject
    lateinit var updateDevicesInRoomUseCase: UpdateDevicesInRoomUseCase

    @Inject
    lateinit var updateDeviceStatusUseCase: UpdateDeviceStatusUseCase

    @Inject
    lateinit var getSensorUseCase: GetSensorUseCase

    private var liveRoom = MutableLiveData<Room>()
    private var liveSensor = MutableLiveData<Sensor>()

    private var imageResource = MutableLiveData<Int>()

    private var adapter = DeviceAdapter().apply {
        setItemClickListener(object : OnItemClick<Device?> {
            override fun onItemClick(position: Int, view: View?, t: Device?) {
                val status = if (t?.status == STATUS.ON.value) {
                    STATUS.OFF.value
                } else STATUS.ON.value
                val param = UpdateDeviceStatusRequest(status)
                val pair = Pair(t!!.db_id, param)
                updateDeviceStatus(pair)
            }
        })
    }

    override fun onViewModelCreated() {
        super.onViewModelCreated()
        val room: Room = arguments?.getSerializable(ROOM) as Room
        liveRoom.value = room
        getDevices(liveRoom.value!!.id)
        getSensors(GetSensorRequest(0, 0))

        setImageResource()
    }

    private fun setImageResource() {
        when (liveRoom.value?.name) {
            "Bathroom" -> {
                imageResource.value = R.drawable.room_template_4
            }
            "Bedroom" -> {
                imageResource.value = R.drawable.room_template_3
            }
            "Kitchen" -> {
                imageResource.value = R.drawable.room_template_2
            }
            "Living Room" -> {
                imageResource.value = R.drawable.room_template_1
            }
        }
    }

    override fun onAttachLifecycle(owner: LifecycleOwner) {
        super.onAttachLifecycle(owner)
        owner.lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        getDevices(liveRoom.value!!.id)
        getSensors(GetSensorRequest(0, 0))
    }

    override fun onBack() {
        scene?.navBack()
    }

    override fun getRoom() = liveRoom

    override fun getAdapter() = adapter

    override fun getSensor() = liveSensor

    override fun getImageResource() = imageResource

    override fun addDevice() {
        liveRoom.value?.let { scene?.onNavigate(it) }
    }

    override fun onTurnOffAllDevices() {
        val param = UpdateDeviceStatusRequest(STATUS.OFF.value)
        val pair = liveRoom.value?.let { Pair(it.id, param) }
        fetch(
            updateDevicesInRoomUseCase,
            object : ApiCallback<GetDevicesInRoomResponse>(LoadingType.BLOCKING) {
                override fun onApiResponseSuccess(response: GetDevicesInRoomResponse) {
                    Timber.d(response.message)
                    getDevices(liveRoom.value!!.id)
                }
            }, pair
        )
    }

    private fun getDevices(roomId: Int) {
        fetch(
            getDevicesInRoomUseCase,
            object : ApiCallback<GetDevicesInRoomResponse>(LoadingType.BLOCKING) {
                override fun onApiResponseSuccess(response: GetDevicesInRoomResponse) {
                    adapter.setData(response.devices)
                }
            }, roomId
        )
    }

    private fun getSensors(getSensorRequest: GetSensorRequest) {
        fetch(
            getSensorUseCase,
            object : ApiCallback<GetSensorResponse>(LoadingType.NONE) {
                override fun onApiResponseSuccess(response: GetSensorResponse) {
                    liveSensor.value = response.sensor
                }
            }, getSensorRequest
        )
    }

    private fun updateDeviceStatus(pair: Pair<String, UpdateDeviceStatusRequest>) {
        fetch(
            updateDeviceStatusUseCase,
            object : ApiCallback<UpdateDeviceStatusResponse>(LoadingType.BLOCKING) {
                override fun onApiResponseSuccess(response: UpdateDeviceStatusResponse) {
                    Timber.d(response.message)
                    getDevices(liveRoom.value!!.id)
                }
            }, pair
        )
    }
}