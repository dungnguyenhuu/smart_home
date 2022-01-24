package com.example.smarthomeapp.presentation.room_detail

import android.content.Context
import android.content.Intent
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.smarthomeapp.BR
import com.example.smarthomeapp.R
import com.example.smarthomeapp.base.scene.MvvmActivity
import com.example.smarthomeapp.data.pojo.room.Room
import com.example.smarthomeapp.presentation.room_detail.contract.RoomDetailContract
import com.example.smarthomeapp.presentation.room_detail.contract.RoomDetailViewModel

const val ROOM: String = "ROOM"

fun Context.startRoom(room: Room) {
    Intent(this, RoomDetailActivity::class.java).apply {
        putExtra(ROOM, room)
    }.let {
        startActivity(it)
    }
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
}