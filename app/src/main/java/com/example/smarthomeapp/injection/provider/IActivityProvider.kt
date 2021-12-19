package com.example.smarthomeapp.injection.provider

import com.example.smarthomeapp.injection.scope.ActivityScope
import com.example.smarthomeapp.presentation.authentication.AuthenticationActivity
import com.example.smarthomeapp.presentation.main.MainActivity
import com.example.smarthomeapp.presentation.room_detail.RoomDetailActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface IActivityProvider {

    @ContributesAndroidInjector
    @ActivityScope
    fun provideMainActivity(): MainActivity

    @ContributesAndroidInjector
    @ActivityScope
    fun provideRoomDetailActivity(): RoomDetailActivity

    @ContributesAndroidInjector
    @ActivityScope
    fun provideAuthenticationActivity(): AuthenticationActivity
}