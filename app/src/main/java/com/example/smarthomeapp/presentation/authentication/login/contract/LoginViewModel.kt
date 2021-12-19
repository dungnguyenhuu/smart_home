package com.example.smarthomeapp.presentation.authentication.login.contract

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.smarthomeapp.base.annotation.LoadingType
import com.example.smarthomeapp.base.viewmodel.AndroidIteratorViewModel
import com.example.smarthomeapp.data.pojo.authentication.LoginRequest
import com.example.smarthomeapp.data.pojo.authentication.LoginResponse
import com.example.smarthomeapp.domain.authentication.LoginUseCase
import javax.inject.Inject

class LoginViewModel @Inject constructor(application: Application) :
    AndroidIteratorViewModel<LoginContract.Scene>(application), LoginContract.ViewModel {

    @Inject
    lateinit var loginUseCase: LoginUseCase

    private val liveEmail = MutableLiveData<String>()
    private val livePassword = MutableLiveData<String>()

    override fun getEmail() = liveEmail

    override fun getPassword() = livePassword

    override fun onClickLogin() {
        val email = liveEmail.value ?: ""
        val password = livePassword.value ?: ""
        //requestLogin(LoginRequest(email, password))
        scene?.navigateMainAct()
    }

    override fun onClickNavigateRegister() {
        scene?.navigateRegister()
    }

    private fun requestLogin(loginRequest: LoginRequest) {
        fetch(
            loginUseCase,
            object : ApiCallback<LoginResponse>(loadingType = LoadingType.BLOCKING) {

                override fun onApiResponseSuccess(response: LoginResponse) {
                    scene?.navigateMainAct()
                }

                override fun onApiResponseFailed(response: LoginResponse) {
                    super.onApiResponseFailed(response)
                    scene?.toast(response.message)
                }

            },
            loginRequest
        )
    }
}