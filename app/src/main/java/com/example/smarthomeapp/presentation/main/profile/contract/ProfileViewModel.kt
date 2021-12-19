package com.example.smarthomeapp.presentation.main.profile.contract

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.smarthomeapp.base.viewmodel.AndroidIteratorViewModel
import com.example.smarthomeapp.data.pojo.user.User
import javax.inject.Inject

class ProfileViewModel @Inject constructor(application: Application) :
    AndroidIteratorViewModel<ProfileContract.Scene>(application),
    ProfileContract.ViewModel {

    private val liveUser = MutableLiveData<User>()

    override fun getUser() = liveUser

    override fun onViewModelCreated() {
        super.onViewModelCreated()
        getUserInfo()
    }

    private fun getUserInfo() {
        liveUser.value =
            User("thangnm1999@gmail.com", "123456", "Minh Thang", "admin", "20-12-2020", "23-12-2020")
    }
}