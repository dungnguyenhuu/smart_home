package com.example.smarthomeapp.presentation.authentication.login.contract

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.example.smarthomeapp.base.scene.BaseContract

interface LoginContract {

    interface Scene : BaseContract.Scene {

        fun navigateMainAct()

        fun navigateRegister()

    }

    interface ViewModel : BaseContract.ViewModel<Scene> {

        fun getEmail(): MutableLiveData<String>

        fun getPassword(): MutableLiveData<String>

        fun onClickLogin()

        fun onClickNavigateRegister()

    }

}