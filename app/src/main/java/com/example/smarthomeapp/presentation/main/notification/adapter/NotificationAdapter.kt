package com.example.smarthomeapp.presentation.main.notification.adapter

import com.example.smarthomeapp.R
import com.example.smarthomeapp.base.adapter.flex.Binder
import com.example.smarthomeapp.base.adapter.flex.LoadmoreFlexibleArrayAdapter
import com.example.smarthomeapp.data.pojo.notification.Notification
import com.example.smarthomeapp.databinding.ItemNotificationBinding

class NotificationAdapter : LoadmoreFlexibleArrayAdapter<Notification>() {

    private val binder = Binder<ItemNotificationBinding, Notification> { _, view, model ->
        view.run {
            model?.let {
                when(it.type) {
                    0 -> imgIcon.setImageResource(R.drawable.ic_light)
                    1 -> imgIcon.setImageResource(R.drawable.ic_fan)
                    2 -> imgIcon.setImageResource(R.drawable.ic_door)
                    3 -> imgIcon.setImageResource(R.drawable.ic_speaker)
                    4 -> imgIcon.setImageResource(R.drawable.ic_no_wifi)
                }
//                imgIcon.setImageResource(R.drawable.nav_notification)
                tvTitle.text = it.title
                tvDescription.text = it.description
            } ?: run {
                imgIcon.setImageDrawable(null)
                tvTitle.text = null
                tvDescription.text = null
            }
            executePendingBindings()
        }
    }

    override fun onCreateBinder(viewType: Int) = R.layout.item_notification to binder

}