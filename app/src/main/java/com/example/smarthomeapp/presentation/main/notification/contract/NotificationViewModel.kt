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
        val noti1 = Notification(1, "Noti 1", "First notification")
        val noti2 = Notification(2, "Noti 2", "Second notification")
        val noti3 = Notification(3, "Noti 3", "Third notification")
        val noti4 = Notification(4, "Noti 4", "Fourth notification")
        val noti5 = Notification(5, "Noti 5", "Fifth notification")
        val noti6 = Notification(6, "Noti 6", "Sixth notification")
        val noti7 = Notification(7, "Noti 7", "Seventh notification")
        val noti8 = Notification(8, "Noti 8", "Eighth notification")
        val noti9 = Notification(9, "Noti 9", "Ninth notification")
        val noti10 = Notification(10, "Noti 10", "Tenth notification")
        val noti11 = Notification(11, "Noti 11", "Eleventh notification")
        val noti12 = Notification(12, "Noti 12", "Twelfth notification")

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