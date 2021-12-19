package com.example.smarthomeapp.presentation.main.home.adapter

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.example.smarthomeapp.R
import com.example.smarthomeapp.base.adapter.BindingArrayAdapter
import com.example.smarthomeapp.base.holder.BindingHolder
import com.example.smarthomeapp.data.pojo.room.Room
import com.example.smarthomeapp.databinding.ItemRoomBinding

class RoomAdapter : BindingArrayAdapter<Room>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BindingHolder<out ViewDataBinding, Room?> =
        RoomViewHolder(parent).also {
            setHolderRootViewAsItemClick(it)
        }

    inner class RoomViewHolder(parent: ViewGroup) :
        BindingHolder<ItemRoomBinding, Room?>(parent, R.layout.item_room) {

        private val imgs = intArrayOf(
            R.drawable.ic_bathroom,
            R.drawable.ic_bedroom,
            R.drawable.ic_kitchen,
            R.drawable.ic_living_room
        )

        override fun onBind(position: Int, model: Room?) {
            super.onBind(position, model)
            binder.run {
                room = model
                when (room?.name) {
                    "Bathroom" -> imgRoom.setImageResource(imgs[0])
                    "Bedroom" -> imgRoom.setImageResource(imgs[1])
                    "Kitchen" -> imgRoom.setImageResource(imgs[2])
                    "Living Room" -> imgRoom.setImageResource(imgs[3])
                }
            }
        }
    }
}