package com.example.smarthomeapp.presentation.authentication

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.smarthomeapp.BR
import com.example.smarthomeapp.R
import com.example.smarthomeapp.base.scene.MvvmActivity
import com.example.smarthomeapp.presentation.authentication.contract.AuthenticationContract
import com.example.smarthomeapp.presentation.authentication.contract.AuthenticationViewModel

class AuthenticationActivity :
    MvvmActivity<AuthenticationContract.Scene, AuthenticationContract.ViewModel>(),
    AuthenticationContract.Scene {

    override fun onCreateViewDataBinding() =
        DataBindingUtil.setContentView<ViewDataBinding>(this, R.layout.activity_authentication)

    override fun getViewModelClass() = AuthenticationViewModel::class.java

    override fun getViewModelVariableId() = BR.vm

}