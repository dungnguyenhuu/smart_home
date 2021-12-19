package com.example.smarthomeapp

//import com.google.firebase.FirebaseApp
import com.example.smarthomeapp.base.scene.BaseApplication
import com.example.smarthomeapp.injection.DaggerAppInjector
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class App : BaseApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {

        return DaggerAppInjector.builder()
            .application(this)
            .build().also {
                it.inject(this)
            }
    }

    /*override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }*/

}