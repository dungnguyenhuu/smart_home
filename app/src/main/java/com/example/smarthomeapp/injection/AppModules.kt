package com.example.smarthomeapp.injection

import android.app.Application
import android.content.Context
import com.example.smarthomeapp.App
import com.example.smarthomeapp.base.domain.fetcher.UseCaseFetcher
import com.example.smarthomeapp.base.domain.fetcher.impl.RxUseCaseFetcherImpl
import com.example.smarthomeapp.data.repository.ClientRepository
import com.example.smarthomeapp.data.repository.ClientRepositoryImpl
import com.example.smarthomeapp.module.credential.CredentialManager
import com.example.smarthomeapp.module.credential.CredentialManagerImpl
import com.example.smarthomeapp.module.restful.RestfulApiModule
import com.example.smarthomeapp.module.serializer.GsonSerializer
import com.example.smarthomeapp.module.serializer.Serializer
import dagger.Binds
import dagger.Module
import io.reactivex.rxjava3.disposables.Disposable
import javax.inject.Singleton

@Module(includes = [RestfulApiModule::class])
interface AppModules {

    @Binds
    fun bindContext(application: App): Context

    @Binds
    fun bindApplication(application: App): Application

    @Binds
    fun bindUseCaseFetcher(impl: RxUseCaseFetcherImpl): UseCaseFetcher<Disposable>

    @Binds
    fun bindSerializer(impl: GsonSerializer): Serializer

    @Binds
    @Singleton
    fun bindClientRepository(impl: ClientRepositoryImpl): ClientRepository

    @Binds
    @Singleton
    fun bindCredentialManager(impl: CredentialManagerImpl): CredentialManager
}