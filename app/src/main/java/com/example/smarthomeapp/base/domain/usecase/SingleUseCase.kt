package com.example.smarthomeapp.base.domain.usecase

import com.example.smarthomeapp.base.domain.callback.Callback
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

abstract class SingleUseCase<RESULT, PARAMS> : RxUseCase<RESULT, PARAMS>() {
    abstract fun create(params: PARAMS?): Single<out RESULT>
    override fun run(callback: Callback<in RESULT>, params: PARAMS?): Disposable {
        return create(params).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { callback.onStart() }
            .subscribe({
                callback.onSuccess(it)
                callback.onCompleted()
            }, {
                callback.onError(it)
                callback.onCompleted()
            })
            .also { addToDisposable(it) }
    }

    protected fun errorParamsSingle(message: String): Single<out RESULT> {
        return Single.error(IllegalArgumentException("${javaClass.simpleName} invalid params $message"))
    }

    fun errorParamsSingle(): Single<out RESULT> {
        return Single.error(IllegalArgumentException("${javaClass.simpleName} invalid params"))
    }

}
