package com.example.smarthomeapp.presentation.device

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.smarthomeapp.BR
import com.example.smarthomeapp.R
import com.example.smarthomeapp.base.scene.MvvmActivity
import com.example.smarthomeapp.data.pojo.room.Room
import com.example.smarthomeapp.presentation.device.contract.DeviceContract
import com.example.smarthomeapp.presentation.device.contract.DeviceViewModel

const val ROOM: String = "ROOM"
const val POSITION: String = "POSITION"


fun Context.startDevice(room: Room, position: Int) {
    Intent(this, DeviceActivity::class.java).apply {
        putExtra(ROOM, room)
        putExtra(POSITION, position)
        Log.i("tesss","Position: "+ position)
    }.let {
        startActivity(it)
    }
}

class DeviceActivity: MvvmActivity<DeviceContract.Scene, DeviceContract.ViewModel>(),
    DeviceContract.Scene {
    override fun onCreateViewDataBinding() =
        DataBindingUtil.setContentView<ViewDataBinding>(this, R.layout.fragment_add_device)

    override fun getViewModelClass() = DeviceViewModel::class.java

    override fun getViewModelVariableId() = BR.vm

    override fun navBack() {
        onBackPressed()
    }
}