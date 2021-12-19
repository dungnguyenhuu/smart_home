package com.example.smarthomeapp.presentation.authentication.login

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.smarthomeapp.BR
import com.example.smarthomeapp.R
import com.example.smarthomeapp.base.scene.MvvmFragment
import com.example.smarthomeapp.presentation.authentication.login.contract.LoginContract
import com.example.smarthomeapp.presentation.authentication.login.contract.LoginViewModel
import com.example.smarthomeapp.util.navigation.findNavController

class LoginFragment : MvvmFragment<LoginContract.Scene, LoginContract.ViewModel>(),
    LoginContract.Scene {

    override fun onCreateViewDataBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): ViewDataBinding =
        DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)

    override fun getViewModelClass() = LoginViewModel::class.java

    override fun getViewModelVariableId() = BR.vm

    override fun navigateMainAct() {
        requireActivity().finish()
        findNavController().navigate(LoginFragmentDirections.toMainActivity())
    }

    override fun navigateRegister() {
        findNavController().navigate(LoginFragmentDirections.toRegister())
    }

}