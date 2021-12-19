package com.example.smarthomeapp.presentation.authentication.contract

import com.example.smarthomeapp.base.scene.BaseContract

interface AuthenticationContract {

    interface Scene : BaseContract.Scene

    interface ViewModel : BaseContract.ViewModel<Scene>

}