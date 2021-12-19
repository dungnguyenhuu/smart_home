package com.example.smarthomeapp.base.scene

import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smarthomeapp.base.dialog.ConfirmBuilder
import com.example.smarthomeapp.base.dialog.ConfirmDialog
import com.example.smarthomeapp.base.dialog.LoadingDialog
import javax.inject.Inject

abstract class MvvmActivity<SCENE : BaseContract.Scene, VIEWMODEL : BaseContract.ViewModel<SCENE>>
    : BaseActivity<SCENE, VIEWMODEL>(),
    BaseContract.Scene {

    @Inject
    lateinit var factory: ViewModelProvider.AndroidViewModelFactory

    override fun provideLoadingDialog(): DialogFragment = LoadingDialog.newInstance()

    override fun provideConfirmDialog(builder: ConfirmBuilder) = ConfirmDialog(builder)

    override fun <VM : ViewModel> createViewModel(vmClass: Class<VM>) =
        ViewModelProvider(this, factory).get(vmClass)

}
