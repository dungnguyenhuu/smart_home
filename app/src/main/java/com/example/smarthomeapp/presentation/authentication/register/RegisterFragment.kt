package com.example.smarthomeapp.presentation.authentication.register

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.smarthomeapp.BR
import com.example.smarthomeapp.R
import com.example.smarthomeapp.base.scene.MvvmFragment
import com.example.smarthomeapp.presentation.authentication.register.contract.RegisterContract
import com.example.smarthomeapp.presentation.authentication.register.contract.RegisterViewModel
import com.example.smarthomeapp.util.navigation.findNavController

class RegisterFragment : MvvmFragment<RegisterContract.Scene, RegisterContract.ViewModel>(),
    RegisterContract.Scene {

    override fun onCreateViewDataBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): ViewDataBinding =
        DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)

    override fun getViewModelClass() = RegisterViewModel::class.java

    override fun getViewModelVariableId() = BR.vm

    override fun navigateLogin() {
        findNavController().navigate(RegisterFragmentDirections.toLogin())
    }

}