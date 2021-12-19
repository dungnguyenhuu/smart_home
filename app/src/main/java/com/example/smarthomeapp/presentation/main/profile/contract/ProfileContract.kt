package com.example.smarthomeapp.presentation.main.profile.contract

import androidx.lifecycle.LiveData
import com.example.smarthomeapp.base.scene.BaseContract
import com.example.smarthomeapp.data.pojo.user.User

interface ProfileContract {

    interface Scene : BaseContract.Scene

    interface ViewModel : BaseContract.ViewModel<Scene> {

        fun getUser(): LiveData<User>

    }

}