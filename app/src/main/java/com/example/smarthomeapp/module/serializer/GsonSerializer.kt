package com.example.smarthomeapp.module.serializer

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.lang.reflect.Type
import javax.inject.Inject

class GsonSerializer @Inject constructor() : Serializer {
    private val gson: Gson = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()

    override fun serialize(source: Any?): String {
        return gson.toJson(source)
    }

    override fun <T> deserialize(source: String?, clazz: Class<T>?): T {
        return gson.fromJson(source, clazz)
    }

    override fun <T> deserialize(source: String?, typeOfT: Type?): T {
        return gson.fromJson(source, typeOfT)
    }
}