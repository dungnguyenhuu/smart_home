package com.example.smarthomeapp.presentation.main.adapter

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.smarthomeapp.presentation.blank.BlankFragment
import com.example.smarthomeapp.presentation.main.contract.MainContract
import com.example.smarthomeapp.presentation.main.home.HomeFragment
import com.example.smarthomeapp.presentation.main.notification.NotificationFragment
import com.example.smarthomeapp.presentation.main.profile.ProfileFragment

class MainAdapter(fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int) = when (position) {
        MainContract.Const.NAV_HOME -> HomeFragment.newInstance()
        MainContract.Const.NAV_NOTIFICATION -> NotificationFragment.newInstance()
        MainContract.Const.NAV_PROFILE -> ProfileFragment.newInstance()
        else -> BlankFragment.newInstance("BLANK")
    }

    override fun getCount() = MainContract.Const.PAGE_COUNT

}