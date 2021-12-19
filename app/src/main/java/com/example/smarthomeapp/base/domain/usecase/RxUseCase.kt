package com.example.smarthomeapp.base.domain.usecase

import io.reactivex.rxjava3.disposables.Disposable
import java.lang.ref.WeakReference

abstract class RxUseCase<RESULT, PARAMS> : UseCase<Disposable, RESULT, PARAMS> {
    private var disposableRef: WeakReference<Disposable>? = null
    protected fun addToDisposable(disposable: Disposable) {
        disposableRef = WeakReference(disposable)
    }

    override fun cancel() {
        disposableRef?.get()?.run {
            if (!isDisposed) {
                dispose()
            }
        }
        disposableRef = null
    }

    override fun isRunning(): Boolean {
        return disposableRef?.get()?.let {
            !it.isDisposed
        } ?: false
    }
}