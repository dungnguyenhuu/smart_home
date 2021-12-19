package com.example.smarthomeapp.presentation.main.contract

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.MutableLiveData
import com.example.smarthomeapp.base.scene.BaseContract
import com.example.smarthomeapp.presentation.main.MainActivity

interface MainContract {
    interface Scene : BaseContract.Scene {

        fun provideFragmentManager(): FragmentManager

        fun provideContext(): MainActivity

        fun navHome()

        fun navNotification()

        fun navProfile()

    }

    interface ViewModel : BaseContract.ViewModel<Scene> {

        fun getPagerAdapter(): FragmentStatePagerAdapter

        fun getCurrentPage(): MutableLiveData<Int>

        fun onNavigate(destination: Int)

    }

    object Const {

        const val PAGE_COUNT = 3

        const val NAV_NOTIFICATION = 0
        const val NAV_HOME = 1
        const val NAV_PROFILE = 2

        fun isNavigatePage(destination: Int) = destination < PAGE_COUNT
    }
}