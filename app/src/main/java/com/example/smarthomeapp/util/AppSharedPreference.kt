package com.example.smarthomeapp.util

import android.content.Context
import android.content.SharedPreferences
import com.example.smarthomeapp.module.serializer.Serializer

@Suppress("unused")
class AppSharedPreferences(
    private val preferences: SharedPreferences,
    private val serializer: Serializer,
) : SharedPreferences by preferences {

    constructor(
        context: Context,
        name: String,
        serializer: Serializer,
    ) : this(context.getSharedPreferences(name, Context.MODE_PRIVATE), serializer)

    fun getString(key: String): String? = getString(key, null)

    fun getStringSet(key: String): MutableSet<String>? = getStringSet(key, null)

    fun putSerializable(key: String, obj: Any?) {
        obj?.let {
            serializer.serialize(it).let {
                if (it.isNullOrEmpty()) delete(key)
                else save(key, it)
            }
        } ?: delete(key)
    }

    fun <T> getSerializable(key: String, clazz: Class<T>): T? =
        getString(key, null)?.let {
            serializer.deserialize(it, clazz)
        }

    inline fun <reified T> getSerializable(key: String): T? = getSerializable(key, T::class.java)

    fun clears(vararg names: String) {
        applyChanges {
            names.forEach { remove(it) }
        }
    }

    inline fun clear(predicate: (String) -> Boolean) {
        applyChanges {
            all.keys.forEach {
                if (predicate(it)) remove(it)
            }
        }
    }
}

inline fun SharedPreferences.applyChanges(
    changes: SharedPreferences.Editor.() -> Unit,
) {
    edit().run {
        changes()
        apply()
    }
}

inline fun SharedPreferences.commitChanges(
    changes: SharedPreferences.Editor.() -> Unit,
) {
    edit().run {
        changes()
        commit()
    }
}

fun SharedPreferences.save(key: String, value: String?) {
    edit().putString(key, value).apply()
}

fun SharedPreferences.save(key: String, value: Int) {
    edit().putInt(key, value).apply()
}

fun SharedPreferences.save(key: String, value: Long) {
    edit().putLong(key, value).apply()
}

fun SharedPreferences.save(key: String, value: Boolean) {
    edit().putBoolean(key, value).apply()
}

fun SharedPreferences.delete(key: String) {
    edit().remove(key).apply()
}

fun SharedPreferences.clear() {
    edit().clear().apply()
}