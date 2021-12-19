package com.example.smarthomeapp.base.scene

import android.content.res.Resources
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.lifecycle.LifecycleOwner
import com.example.smarthomeapp.base.annotation.LoadingType
import com.example.smarthomeapp.base.dialog.ConfirmBuilder

interface BaseContract {
    interface Scene {
        fun getSceneResources(): Resources?

        fun getSceneArguments(): Bundle?

        fun showLoading(@LoadingType loadingType: Int)

        fun hideLoading(@LoadingType loadingType: Int)

        fun showMessage(builder: ConfirmBuilder)

        fun toast(message: String?)

        fun toast(@StringRes message: Int)

        fun onApplicationFailure(errorCode: Int, message: String?)

        fun onNoNetworkConnection()

        fun requestSceneBackward()

        fun requestSceneFinish()
    }

    interface ViewModel<SCENE : Scene> {
        fun isSceneAttached(): Boolean

        fun onAttachToScene(scene: SCENE)

        fun onAttachLifecycle(owner: LifecycleOwner)

        fun onViewModelCreated()

        fun onViewModelDestroy()

        fun onDetachLifecycle(owner: LifecycleOwner)

        fun onDetachFromScene()
    }
}