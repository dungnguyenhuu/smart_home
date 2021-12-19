package com.example.smarthomeapp.module.serializer

import dagger.Binds
import dagger.Module

@Module
interface SerializerModule {

    @Binds
    fun bindSerializer(gsonSerializer: GsonSerializer): Serializer

}