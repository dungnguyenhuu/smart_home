package com.example.smarthomeapp.presentation.room_detail.contract

import android.app.Application
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.*
import com.example.smarthomeapp.R
import com.example.smarthomeapp.base.adapter.OnItemClick
import com.example.smarthomeapp.base.annotation.LoadingType
import com.example.smarthomeapp.base.viewmodel.AndroidIteratorViewModel
import com.example.smarthomeapp.data.pojo.device.*
import com.example.smarthomeapp.data.pojo.room.GetDevicesInRoomResponse
import com.example.smarthomeapp.data.pojo.room.Room
import com.example.smarthomeapp.data.pojo.sensor.GetSensorRequest
import com.example.smarthomeapp.data.pojo.sensor.GetSensorResponse
import com.example.smarthomeapp.data.pojo.sensor.Sensor
import com.example.smarthomeapp.domain.device.UpdateDeviceModeUseCase
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

    val lightIcons = arrayListOf<Int>(
        R.drawable.ic_lighting_off,
        R.drawable.ic_lighting_off,
        R.drawable.ic_lighting_off,
        R.drawable.ic_lighting_off,
    )

    @Inject
    lateinit var getDevicesInRoomUseCase: GetDevicesInRoomUseCase

    @Inject
    lateinit var updateDevicesInRoomUseCase: UpdateDevicesInRoomUseCase

    @Inject
    lateinit var updateDeviceStatusUseCase: UpdateDeviceStatusUseCase

    @Inject
    lateinit var updateDeviceModeUseCase: UpdateDeviceModeUseCase

    @Inject
    lateinit var getSensorUseCase: GetSensorUseCase

    private var liveRoom = MutableLiveData<Room>()

    private var liveSensor = MutableLiveData<Sensor>()

    private var imageResource = MutableLiveData<Int>()

    private var liveLightIcons = MutableLiveData<ArrayList<Int>>()

    private var numberDevice = MutableLiveData<Int>()

    var devices = arrayListOf<Device>()

    private var adapter = DeviceAdapter().apply {
        setItemClickListener(object : OnItemClick<Device?> {
            override fun onItemClick(position: Int, view: View?, t: Device?) {
                if (view?.id == R.id.sw_device) {
                    var status = ""
                    if (t?.status == STATUS.ON.value) {
                        status = STATUS.OFF.value
                        lightIcons[position] = R.drawable.ic_lighting_off
                    } else {
                        status = STATUS.ON.value
                        lightIcons[position] = R.drawable.ic_lighting_on
                    }
                    liveLightIcons.value=lightIcons
                    val param = UpdateDeviceStatusRequest(status)
                    val pair = Pair(t!!.db_id, param)
                    updateDeviceStatus(pair)
                } else {
                    val mode = if (t?.mode == MODE.MANUAL.value) {
                        MODE.AUTO.value
                    } else MODE.MANUAL.value
                    val param = UpdateDeviceModeRequest(mode)
                    val pair = Pair(t!!.db_id, param)
                    updateDeviceMode(pair)
                }
            }
        })
    }

    override fun onViewModelCreated() {
        super.onViewModelCreated()
        val room: Room = arguments?.getSerializable(ROOM) as Room
        liveRoom.value = room
        getDevices(liveRoom.value!!.id)
        getSensors(GetSensorRequest(0, 0))
//        setImageResource()
        liveLightIcons.value= lightIcons

    }

//    private fun setImageResource() {
//        when (liveRoom.value?.name) {
//            "Bathroom" -> {
//                imageResource.value = R.drawable.room_template_4
//            }
//            "Bedroom" -> {
//                imageResource.value = R.drawable.room_template_3
//            }
//            "Kitchen" -> {
//                imageResource.value = R.drawable.room_template_2
//            }
//            "Living Room" -> {
//                imageResource.value = R.drawable.room_template_1
//            }
//        }
//    }

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

    override fun getNumberDevice() = numberDevice


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

    override fun getIcon() = liveLightIcons

    override fun getIdDevice(position : Int) = devices[position].db_id

    override fun getListDevice() = devices

    fun getIconLightResource() = liveLightIcons

    private fun getDevices(roomId: Int) {
        fetch(
            getDevicesInRoomUseCase,
            object : ApiCallback<GetDevicesInRoomResponse>(LoadingType.BLOCKING) {
                override fun onApiResponseSuccess(response: GetDevicesInRoomResponse) {
                    adapter.setData(response.devices)
                    devices = response.devices as ArrayList<Device>
                    for (i in 0 until devices.size){
                        if(devices[i].status == STATUS.ON.value){
                            liveLightIcons.value!![i]=R.drawable.ic_lighting_on
                        }else{
                            liveLightIcons.value!![i]=R.drawable.ic_lighting_off
                        }
                    }

                    numberDevice.value = devices.size
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

    override fun updateDeviceStatus(pair: Pair<String, UpdateDeviceStatusRequest>) {
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

    private fun updateDeviceMode(pair: Pair<String, UpdateDeviceModeRequest>) {
        fetch(
            updateDeviceModeUseCase,
            object : ApiCallback<UpdateDeviceModeResponse>(LoadingType.BLOCKING) {
                override fun onApiResponseSuccess(response: UpdateDeviceModeResponse) {
                    Timber.d(response.message)
                    getDevices(liveRoom.value!!.id)
                }
            }, pair
        )
    }
}