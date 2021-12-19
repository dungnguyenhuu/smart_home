package com.example.smarthomeapp.presentation.main.notification

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.example.smarthomeapp.BR
import com.example.smarthomeapp.R
import com.example.smarthomeapp.base.scene.MvvmFragment
import com.example.smarthomeapp.presentation.main.notification.contract.NotificationContract
import com.example.smarthomeapp.presentation.main.notification.contract.NotificationViewModel

class NotificationFragment :
    MvvmFragment<NotificationContract.Scene, NotificationContract.ViewModel>(),
    NotificationContract.Scene {

    override fun onCreateViewDataBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): ViewDataBinding =
        DataBindingUtil.inflate(inflater, R.layout.fragment_notification, container, false)

    override fun getViewModelClass(): Class<out ViewModel> = NotificationViewModel::class.java

    override fun getViewModelVariableId() = BR.vm

    companion object {
        fun newInstance() = NotificationFragment()
    }

}