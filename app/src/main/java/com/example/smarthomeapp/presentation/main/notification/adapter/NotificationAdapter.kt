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
                imgIcon.setImageResource(R.drawable.nav_notification)
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