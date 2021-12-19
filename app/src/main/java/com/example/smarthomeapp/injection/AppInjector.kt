package com.example.smarthomeapp.injection

import com.example.smarthomeapp.App
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModules::class, AppProvider::class, AndroidSupportInjectionModule::class])
interface AppInjector : AndroidInjector<App> {

    override fun inject(instance: App)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: App): Builder

        fun build(): AppInjector
    }

}