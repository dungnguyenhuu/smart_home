package com.example.smarthomeapp.presentation.room_detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.example.smarthomeapp.BR
import com.example.smarthomeapp.R
import com.example.smarthomeapp.base.annotation.LoadingType
import com.example.smarthomeapp.base.scene.MvvmActivity
import com.example.smarthomeapp.data.pojo.device.MODE
import com.example.smarthomeapp.data.pojo.device.STATUS
import com.example.smarthomeapp.data.pojo.device.UpdateDeviceStatusRequest
import com.example.smarthomeapp.data.pojo.room.GetDevicesInRoomResponse
import com.example.smarthomeapp.data.pojo.room.Room
import com.example.smarthomeapp.presentation.device.startDevice
import com.example.smarthomeapp.presentation.room_detail.contract.RoomDetailContract
import com.example.smarthomeapp.presentation.room_detail.contract.RoomDetailViewModel
import kotlinx.android.synthetic.main.activity_room_detail.*
import kotlinx.android.synthetic.main.template_1.*
import kotlinx.android.synthetic.main.view_room_info_header.*
import javax.inject.Inject

const val ROOM: String = "ROOM"
const val TEMPLATE: String = "TEMPLATE"
const val TOTAL_DEVICE: String = "TOTAL_DEVICE"
const val LIGHT_DISABLE: Float = 0.3f
const val LIGHT_ENABLE: Float = 1f


fun Context.startRoom(room: Room) {
    Intent(this, RoomDetailActivity::class.java).apply {
        putExtra(ROOM, room)
        putExtra(TEMPLATE, getTemplate(room.name))
        putExtra(TOTAL_DEVICE, room.totalDevice)
    }.let {
        startActivity(it)
    }
}

fun getTemplate(name: String): Int {
    when (name) {
        "Bathroom" -> return R.layout.template_4

        "Bedroom" -> return R.layout.template_3
        "Bedroom 1" -> return R.layout.template_3
        "Bedroom 2" -> return R.layout.template_3
        "Bedroom 3" -> return R.layout.template_3

        "Kitchen" -> return R.layout.template_2

        "Living Room" -> return R.layout.template_1

    }
    return 0
}

class RoomDetailActivity : MvvmActivity<RoomDetailContract.Scene, RoomDetailContract.ViewModel>(),
    RoomDetailContract.Scene {

    override fun onCreateViewDataBinding() =
        DataBindingUtil.setContentView<ViewDataBinding>(this, R.layout.activity_room_detail)

    override fun getViewModelClass() = RoomDetailViewModel::class.java

    override fun getViewModelVariableId() = BR.vm

    override fun navBack() {
        onBackPressed()
    }

    override fun onNavigate(room: Room, position: Int) {
        this.startDevice(room,position)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val templateId = intent.getIntExtra(TEMPLATE, 0)
        val totalDevice = intent.getIntExtra(TOTAL_DEVICE, 0)
        number_total.text= totalDevice.toString()

        val templateLayout: ConstraintLayout = findViewById(R.id.templateLayout)
        val view: View = LayoutInflater.from(this).inflate(templateId, templateLayout)

        val iconObserver = Observer<ArrayList<Int>> { newIcon ->
            viewBinding.apply {
                light1?.setImageResource(newIcon[0])
                light2?.setImageResource(newIcon[1])
                light3?.setImageResource(newIcon[2])
                light4?.setImageResource(newIcon[3])
            }
        }

        val remoteDeviceObserver = Observer<ArrayList<Boolean>> { newRemote ->
            viewBinding.apply {
                light1?.setImageResource(viewModel.getIcon().value!![0])
                light2?.setImageResource(viewModel.getIcon().value!![1])
                light3?.setImageResource(viewModel.getIcon().value!![2])
                light4?.setImageResource(viewModel.getIcon().value!![3])

//                btn_add_device.isEnabled = newNumber < totalDevice
                var active =0;
                newRemote.forEach {
                    if(it) active++
                }
                number_active.text = active.toString()
                if(!newRemote[3]){
//                    light4?.isEnabled = false
                    light_name_4?.text = ""
                    layout_light_4?.alpha = LIGHT_DISABLE
                }else{
//                    light4?.isEnabled= viewModel.getListDevice()[3].mode == MODE.MANUAL.value
                    light_name_4?.text = viewModel.getListDevice()[3].name
                    layout_light_4?.alpha = LIGHT_ENABLE
                }

                if(!newRemote[2]){
//                    light3?.isEnabled = false
                    light_name_3?.text = ""
                    layout_light_3?.alpha = LIGHT_DISABLE
                }else{
//                    light3?.isEnabled = viewModel.getListDevice()[2].mode == MODE.MANUAL.value
                    light_name_3?.text = viewModel.getListDevice()[2].name
                    layout_light_3?.alpha = LIGHT_ENABLE
                }

                if(!newRemote[1]){
//                    light2?.isEnabled = false
                    light_name_2?.text = ""
                    layout_light_2?.alpha = LIGHT_DISABLE
                }else{
//                    light2?.isEnabled = viewModel.getListDevice()[1].mode == MODE.MANUAL.value
                    light_name_2?.text = viewModel.getListDevice()[1].name
                    layout_light_2?.alpha = LIGHT_ENABLE
                }

                if(!newRemote[0]){
//                    light1?.isEnabled = false
                    light_name_1?.text = ""
                    layout_light_1?.alpha = LIGHT_DISABLE
                }else{
//                    light1?.isEnabled = viewModel.getListDevice()[0].mode == MODE.MANUAL.value
                    light_name_1?.text = viewModel.getListDevice()[0].name
                    layout_light_1?.alpha = LIGHT_ENABLE
                }

            }
        }
        viewModel.getIcon().observe(this, iconObserver)
        viewModel.getRemoteDevice().observe(this, remoteDeviceObserver)

        viewBinding.apply {
            //light1 setting
            light1?.setOnClickListener() {
                onClickLightTemplate(0, light1)
            }

            //light2 setting
            light2?.setOnClickListener() {
                onClickLightTemplate(1, light2)
            }

            //light3 setting
            light3?.setOnClickListener() {
                onClickLightTemplate(2, light3)
            }

            //light4 setting
            light4?.setOnClickListener() {
                onClickLightTemplate(3, light4)
            }
        }
    }

    private fun onClickLightTemplate(position: Int, view: ImageView) {
        if(viewModel.getRemoteDevice().value!![position]) {
            var status = ""
            if (viewModel.getIcon().value!![position] == R.drawable.ic_lighting_off) {
                status = STATUS.ON.value
                viewModel.getIcon().value!![position] = R.drawable.ic_lighting_on
            } else {
                status = STATUS.OFF.value
                viewModel.getIcon().value!![position] = R.drawable.ic_lighting_off
            }
            view?.setImageResource(viewModel.getIcon().value!![position])
            val param = UpdateDeviceStatusRequest(status)
            val pair = Pair(viewModel.getIdDevice(0), param)
            viewModel.updateDeviceStatus(pair)
        }else{
            viewModel.addDevice(position)
        }
    }

}