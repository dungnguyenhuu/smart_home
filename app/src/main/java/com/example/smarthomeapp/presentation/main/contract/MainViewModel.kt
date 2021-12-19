package com.example.smarthomeapp.presentation.main.contract

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.smarthomeapp.base.viewmodel.BaseAndroidViewModel
import com.example.smarthomeapp.presentation.main.adapter.MainAdapter
import javax.inject.Inject

class MainViewModel @Inject constructor(application: Application) :
    BaseAndroidViewModel<MainContract.Scene>(application), MainContract.ViewModel {

    private val liveCurrentPage = MutableLiveData(1)

    private lateinit var mainAdapter: MainAdapter

    override fun onAttachToScene(scene: MainContract.Scene) {
        super.onAttachToScene(scene)
        mainAdapter = MainAdapter(scene.provideFragmentManager())
    }

    override fun getPagerAdapter() = mainAdapter

    override fun getCurrentPage() = liveCurrentPage

    override fun onNavigate(destination: Int) {
        @Suppress("ControlFlowWithEmptyBody")
        if (MainContract.Const.isNavigatePage(destination)) {
            liveCurrentPage.value = destination
        } else {
            //TODO request scene to navigation
        }
    }

}