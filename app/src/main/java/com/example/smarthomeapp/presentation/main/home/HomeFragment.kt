package com.example.smarthomeapp.presentation.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.example.smarthomeapp.BR
import com.example.smarthomeapp.R
import com.example.smarthomeapp.base.scene.MvvmFragment
import com.example.smarthomeapp.data.pojo.room.Room
import com.example.smarthomeapp.presentation.main.home.contract.HomeContract
import com.example.smarthomeapp.presentation.main.home.contract.HomeViewModel
import com.example.smarthomeapp.presentation.room_detail.startRoom

class HomeFragment : MvvmFragment<HomeContract.Scene, HomeContract.ViewModel>(),
    HomeContract.Scene {

    override fun onCreateViewDataBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): ViewDataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

    override fun getViewModelClass(): Class<out ViewModel> = HomeViewModel::class.java

    override fun getViewModelVariableId() = BR.vm

    companion object {
        fun newInstance(): HomeFragment {
            val args = Bundle()
            val fragment = HomeFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onNavigate(room: Room) {
        context?.startRoom(room)
    }

}