package com.example.smarthomeapp.presentation.authentication.contract

import android.app.Application
import com.example.smarthomeapp.base.viewmodel.BaseAndroidViewModel
import javax.inject.Inject

class AuthenticationViewModel @Inject constructor(application: Application) :
    BaseAndroidViewModel<AuthenticationContract.Scene>(application),
    AuthenticationContract.ViewModel