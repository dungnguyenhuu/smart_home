package com.example.smarthomeapp.presentation.main.notification.contract

import android.app.Application
import androidx.recyclerview.widget.RecyclerView
import com.example.smarthomeapp.base.viewmodel.AndroidIteratorViewModel
import com.example.smarthomeapp.data.pojo.notification.Notification
import com.example.smarthomeapp.presentation.main.notification.adapter.NotificationAdapter
import com.example.smarthomeapp.util.list.ListController
import com.example.smarthomeapp.util.list.ListController.Listener
import javax.inject.Inject

class NotificationViewModel @Inject constructor(application: Application) :
    AndroidIteratorViewModel<NotificationContract.Scene>(application),
    NotificationContract.ViewModel {

    private val adapter = NotificationAdapter()

    private val controller = ListController(adapter, false, object : Listener {

        override fun onContentLoadmore(totalItemsCount: Int, view: RecyclerView) {
            loadNotification()
        }

        override fun onContentRefresh() {
            loadNotification()
        }

        override fun onRequestTryAgain() {}
    })

    override fun getNotificationController() = controller

    private fun loadNotification() {
        val noti1 = Notification(1, 1, "Fan over heating", "Running for 34 hrs")
        val noti2 = Notification(2, 3,"Speaker battery low", "10% charge remaining")
        val noti3 = Notification(3,0, "Light have been added", "The light has been successfully added to the living room")
        val noti4 = Notification(4, 2,"Front door lock was opened", "Front door lock was opened")
        val noti5 = Notification(5, 1,"Fan over heating", "Running for 35 hrs")
        val noti6 = Notification(6,3, "Speaker battery low", "10% charge remaining")
        val noti7 = Notification(7, 2,"Front door lock was opened", "Front door lock was opened")
        val noti8 = Notification(8,0, "Light have been added", "The light has been successfully added to the bedroom")
        val noti9 = Notification(9,0, "Light have been added", "The light has been successfully added to the living room")
        val noti10 = Notification(10, 1,"Fan over heating", "Tenth notification")
        val noti11 = Notification(11, 4,"Wifi connection lost", "Running for 30 hrs")
        val noti12 = Notification(12,3, "Speaker battery low", "10% charge remaining")

        val notiList = arrayListOf(
            noti1,
            noti2,
            noti3,
            noti4,
            noti5,
            noti6,
            noti7,
            noti8,
            noti9,
            noti10,
            noti11,
            noti12
        )

        adapter.setData(notiList)
    }

    override fun onViewModelCreated() {
        super.onViewModelCreated()
        loadNotification()
    }
}