package com.example.smarthomeapp.base.scene

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

abstract class MvvmFragment<SCENE : BaseContract.Scene, VIEWMODEL : BaseContract.ViewModel<SCENE>>
    : BaseVmFragment<SCENE, VIEWMODEL>(),
    BaseContract.Scene {

    @Inject
    lateinit var factory: ViewModelProvider.AndroidViewModelFactory

    override fun <VM : ViewModel> createViewModel(vmClass: Class<VM>) =
        ViewModelProvider(this, factory).get(vmClass)

}