package com.example.smarthomeapp.base.domain.usecase

import com.example.smarthomeapp.base.domain.callback.Callback

interface UseCase<WATCHER, RESULT, PARAMS> {
    fun cancel()
    fun isRunning(): Boolean
    fun cancelIfRunning() {
        if (isRunning()) {
            cancel()
        }
    }

    fun run(callback: Callback<in RESULT>, params: PARAMS?): WATCHER
}