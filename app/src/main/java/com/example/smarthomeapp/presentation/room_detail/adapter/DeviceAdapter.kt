package com.example.smarthomeapp.presentation.room_detail.adapter

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.example.smarthomeapp.R
import com.example.smarthomeapp.base.adapter.BindingArrayAdapter
import com.example.smarthomeapp.base.holder.BindingHolder
import com.example.smarthomeapp.data.pojo.device.Device
import com.example.smarthomeapp.data.pojo.device.STATUS
import com.example.smarthomeapp.databinding.ItemDeviceBinding

class DeviceAdapter : BindingArrayAdapter<Device>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BindingHolder<out ViewDataBinding, Device?> =
        DeviceViewHolder(parent).also {
            setHolderRootViewAsItemClick(it)
        }

    inner class DeviceViewHolder(parent: ViewGroup) :
        BindingHolder<ItemDeviceBinding, Device?>(parent, R.layout.item_device) {

        private val imgs = intArrayOf(
            R.drawable.ic_light,
            R.drawable.ic_fan,
            R.drawable.ic_door,
            R.drawable.ic_air_conditioner,
        )

        override fun onBind(position: Int, model: Device?) {
            super.onBind(position, model)
            binder.apply {
                device = model
                swDevice.isChecked = device?.status == STATUS.ON.value
                when (device?.type) {
                    0 -> imgDevice.setImageResource(imgs[0])
                    1 -> imgDevice.setImageResource(imgs[1])
                    2 -> imgDevice.setImageResource(imgs[2])
                    3 -> imgDevice.setImageResource(imgs[3])
                    else -> imgDevice.setImageResource(imgs[0])
                }
                registerChildViewAsHolderClickEvent(swDevice, mItemClickListener)
            }
        }
    }
}