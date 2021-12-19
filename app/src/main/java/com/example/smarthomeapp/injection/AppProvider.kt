package com.example.smarthomeapp.injection

import com.example.smarthomeapp.injection.provider.IActivityProvider
import com.example.smarthomeapp.injection.provider.IFragmentProvider
import com.example.smarthomeapp.injection.provider.IViewModelProvider
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import java.util.*
import javax.inject.Singleton

@Module(includes = [IActivityProvider::class, IFragmentProvider::class, IViewModelProvider::class])
class AppProvider {

    @Provides
    @Singleton
    fun provideGson() = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()

    @Provides
    fun provideLocale() = Locale.forLanguageTag("vi-VN")
}