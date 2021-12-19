package com.example.smarthomeapp.base.domain.fetcher.impl

import com.example.smarthomeapp.base.domain.callback.Callback
import com.example.smarthomeapp.base.domain.fetcher.UseCaseFetcher
import com.example.smarthomeapp.base.domain.usecase.UseCase
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import javax.inject.Inject

class RxUseCaseFetcherImpl @Inject constructor() : UseCaseFetcher<Disposable> {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun addWatcher(watcher: Disposable?) {
        watcher?.let {
            compositeDisposable.add(it)
        }
    }

    override fun <RESULT, PARAMS> fetch(
        useCase: UseCase<Disposable, RESULT, PARAMS>,
        callback: Callback<in RESULT>,
        params: PARAMS?
    ) {
        useCase.run(callback, params).also {
            compositeDisposable.add(it)
        }
    }

    override fun <RESULT> fetch(
        useCase: UseCase<Disposable, RESULT, Unit>,
        callback: Callback<in RESULT>
    ) {
        useCase.run(callback, null).also {
            compositeDisposable.add(it)
        }
    }

    override fun cancelAll() {
        compositeDisposable.clear()
    }
}