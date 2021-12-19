package com.example.smarthomeapp.base.domain.callback

import timber.log.Timber

interface Callback<RESULT> {

    fun onStart() {}
    fun onSuccess(result: RESULT)
    fun onError(error: Throwable) {
        Timber.e(error)
    }

    fun onCompleted() {}
}