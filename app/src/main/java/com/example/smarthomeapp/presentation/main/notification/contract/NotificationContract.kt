package com.example.smarthomeapp.presentation.main.notification.contract

import com.example.smarthomeapp.base.scene.BaseContract
import com.example.smarthomeapp.util.list.IListController

interface NotificationContract {

    interface Scene : BaseContract.Scene

    interface ViewModel : BaseContract.ViewModel<Scene> {

        fun getNotificationController(): IListController

    }

}