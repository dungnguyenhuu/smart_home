package com.example.smarthomeapp.base.viewmodel

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import com.example.smarthomeapp.base.scene.BaseContract

open class BaseAndroidViewModel<SCENE : BaseContract.Scene>(application: Application) :
    AndroidViewModel(application),
    BaseContract.ViewModel<SCENE> {

    private var mScene: SCENE? = null

    override fun isSceneAttached() = mScene != null

    override fun onAttachToScene(scene: SCENE) {
        this.mScene = scene
    }

    override fun onAttachLifecycle(owner: LifecycleOwner) {

    }

    override fun onViewModelCreated() {

    }

    override fun onViewModelDestroy() {

    }

    override fun onDetachLifecycle(owner: LifecycleOwner) {

    }

    override fun onDetachFromScene() {
        mScene = null
    }

    protected val scene: SCENE?
        get() = mScene

    protected val resources
        get() = getApplication<Application>().resources

    protected val arguments: Bundle?
        get() = mScene?.getSceneArguments()
}