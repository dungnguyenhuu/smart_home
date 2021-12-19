package com.example.smarthomeapp.presentation.authentication.register.contract

import androidx.lifecycle.MutableLiveData
import com.example.smarthomeapp.base.scene.BaseContract

interface RegisterContract {

    interface Scene : BaseContract.Scene {

        fun navigateLogin()

    }

    interface ViewModel : BaseContract.ViewModel<Scene> {

        fun getName(): MutableLiveData<String>

        fun getEmail(): MutableLiveData<String>

        fun getPassword(): MutableLiveData<String>

        fun getConfirmPassword(): MutableLiveData<String>

        fun onClickRegister()

    }

}