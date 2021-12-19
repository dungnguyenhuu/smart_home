package com.example.smarthomeapp.injection.provider

import com.example.smarthomeapp.injection.scope.FragmentScope
import com.example.smarthomeapp.presentation.authentication.login.LoginFragment
import com.example.smarthomeapp.presentation.authentication.register.RegisterFragment
import com.example.smarthomeapp.presentation.main.home.HomeFragment
import com.example.smarthomeapp.presentation.main.notification.NotificationFragment
import com.example.smarthomeapp.presentation.main.profile.ProfileFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface IFragmentProvider {

    @ContributesAndroidInjector
    @FragmentScope
    fun provideHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    @FragmentScope
    fun provideNotificationFragment(): NotificationFragment

    @ContributesAndroidInjector
    @FragmentScope
    fun provideProfileFragment(): ProfileFragment

    @ContributesAndroidInjector
    @FragmentScope
    fun provideLoginFragment(): LoginFragment

    @ContributesAndroidInjector
    @FragmentScope
    fun provideRegisterFragment(): RegisterFragment
}