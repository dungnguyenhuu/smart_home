package com.example.smarthomeapp.presentation.main

import android.os.Handler
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.smarthomeapp.BR
import com.example.smarthomeapp.R
import com.example.smarthomeapp.base.scene.MvvmActivity
import com.example.smarthomeapp.presentation.main.contract.MainContract
import com.example.smarthomeapp.presentation.main.contract.MainViewModel

class MainActivity : MvvmActivity<MainContract.Scene, MainContract.ViewModel>(),
    MainContract.Scene {

    private var doubleBackToExitPressedOnce = false

    override fun onCreateViewDataBinding(): ViewDataBinding =
        DataBindingUtil.setContentView(this, R.layout.activity_main)

    override fun getViewModelClass() = MainViewModel::class.java

    override fun getViewModelVariableId() = BR.vm

    override fun provideFragmentManager() = supportFragmentManager

    override fun provideContext() = this

    override fun navHome() {
        viewModel.onNavigate(MainContract.Const.NAV_HOME)
    }

    override fun navNotification() {
        viewModel.onNavigate(MainContract.Const.NAV_NOTIFICATION)
    }

    override fun navProfile() {
        viewModel.onNavigate(MainContract.Const.NAV_PROFILE)
    }

    @Suppress("DEPRECATION")
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }
        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, getString(R.string.confirm_exit_app), Toast.LENGTH_SHORT).show()
        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
    }

}