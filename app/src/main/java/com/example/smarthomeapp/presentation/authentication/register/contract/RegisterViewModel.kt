package com.example.smarthomeapp.presentation.authentication.register.contract

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.smarthomeapp.R
import com.example.smarthomeapp.base.viewmodel.AndroidIteratorViewModel
import com.example.smarthomeapp.data.pojo.Response
import com.example.smarthomeapp.data.pojo.authentication.RegisterRequest
import com.example.smarthomeapp.domain.authentication.RegisterUseCase
import com.example.smarthomeapp.util.extensions.ResourceXs.getString
import javax.inject.Inject

class RegisterViewModel @Inject constructor(application: Application) :
    AndroidIteratorViewModel<RegisterContract.Scene>(application), RegisterContract.ViewModel {

    @Inject
    lateinit var registerUseCase: RegisterUseCase

    private var liveName = MutableLiveData<String>()
    private var liveEmail = MutableLiveData<String>()
    private var livePassword = MutableLiveData<String>()
    private var liveConfirmPassword = MutableLiveData<String>()

    override fun getEmail() = liveEmail

    override fun getName() = liveName

    override fun getPassword() = livePassword

    override fun getConfirmPassword() = liveConfirmPassword

    override fun onClickRegister() {
        val name = liveName.value ?: ""
        val email = liveEmail.value ?: ""
        val password = livePassword.value ?: ""
        val confirmPassword = liveConfirmPassword.value ?: ""

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            scene?.toast(getString(R.string.empty_register_information))
        } else if (password != confirmPassword) {
            scene?.toast(getString(R.string.equal_password))
        } else {
            requestRegister(RegisterRequest(name, email, password, "customer"))
        }
    }

    private fun requestRegister(registerRequest: RegisterRequest) {
        fetch(registerUseCase, object : ApiCallback<Response>() {

            override fun onApiResponseSuccess(response: Response) {
                scene?.navigateLogin()
                scene?.toast(getString(R.string.register_success))
            }

            override fun onApiResponseFailed(response: Response) {
                super.onApiResponseFailed(response)
                scene?.toast(response.toString())
            }

        }, registerRequest)
    }
}