package com.example.smarthomeapp.base.domain.fetcher

import com.example.smarthomeapp.base.domain.callback.Callback
import com.example.smarthomeapp.base.domain.usecase.UseCase

interface UseCaseFetcher<WATCHER> {
    fun addWatcher(watcher: WATCHER?)
    fun <RESULT, PARAMS> fetch(
        useCase: UseCase<WATCHER, RESULT, PARAMS>,
        callback: Callback<in RESULT>,
        params: PARAMS?
    )

    fun <RESULT> fetch(
        useCase: UseCase<WATCHER, RESULT, Unit>,
        callback: Callback<in RESULT>
    )

    fun cancelAll()
}