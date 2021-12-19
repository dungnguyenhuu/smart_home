package com.example.smarthomeapp.base.viewmodel

import android.app.Application
import androidx.annotation.CallSuper
import com.example.smarthomeapp.base.annotation.LoadingType
import com.example.smarthomeapp.base.domain.callback.Callback
import com.example.smarthomeapp.base.domain.fetcher.UseCaseFetcher
import com.example.smarthomeapp.base.domain.usecase.UseCase
import com.example.smarthomeapp.base.network.NoConnectivityException
import com.example.smarthomeapp.base.scene.BaseContract
import com.example.smarthomeapp.data.pojo.Response
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import io.reactivex.rxjava3.disposables.Disposable
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

/**
 * For support usecase iteration
 *
 * @param <SCENE>
</SCENE> */
open class AndroidIteratorViewModel<SCENE : BaseContract.Scene>(application: Application) :
    BaseAndroidViewModel<SCENE>(application) {

    @Inject
    lateinit var fetcher: UseCaseFetcher<Disposable>

    fun <RESULT, PARAMS> fetch(
        useCase: UseCase<Disposable, RESULT, PARAMS>,
        callback: Callback<RESULT>,
        params: PARAMS?
    ) {
        fetcher.fetch(useCase, callback, params)
    }

    fun <RESULT> fetch(
        useCase: UseCase<Disposable, RESULT, Unit>,
        callback: Callback<RESULT>
    ) {
        fetcher.fetch(useCase, callback)
    }

    fun <RESULT> fetchSilently(useCase: UseCase<Disposable, RESULT, Unit>) {
        fetcher.fetch(useCase, SilentCallback())
    }

    fun <RESULT, PARAMS> fetchSilently(
        useCase: UseCase<Disposable, RESULT, PARAMS>,
        params: PARAMS?
    ) {
        fetcher.fetch(useCase, SilentCallback(), params)
    }

    fun addWatcher(disposable: Disposable?) {
        fetcher.addWatcher(disposable)
    }

    override fun onViewModelDestroy() {
        super.onViewModelDestroy()
        fetcher.cancelAll()
    }

    abstract inner class BaseCallback<RESULT>(private var loadingType: Int = LoadingType.NONE) :
        Callback<RESULT> {

        @CallSuper
        override fun onStart() {
            onShowLoading(loadingType)
        }

        @CallSuper
        override fun onCompleted() {
            onHideLoading(loadingType)
        }

        protected fun onShowLoading(loadingType: Int) {
            if (loadingType != LoadingType.NONE) {
                scene?.showLoading(loadingType)
            }
        }

        protected fun onHideLoading(loadingType: Int) {
            if (loadingType != LoadingType.NONE) {
                scene?.hideLoading(loadingType)
            }
        }
    }

    abstract inner class IteratorCallback<DATA>(loadingType: Int = LoadingType.NONE) :
        BaseCallback<DATA>(loadingType) {
        final override fun onError(error: Throwable) {
            Timber.e("onError -> ${this@IteratorCallback.javaClass.simpleName} ${error.javaClass.simpleName} ${error.message}")
            if (error is NoConnectivityException) {
                if (shouldHandleNetworkConnection()) {
                    onNetworkConnectFailed()
                }
            } else onInterceptError(error)
        }

        protected fun shouldHandleNetworkConnection(): Boolean {
            return true
        }

        protected fun onNetworkConnectFailed() {
            scene?.onNoNetworkConnection()
        }

        protected open fun onInterceptError(error: Throwable) {

        }
    }

    abstract inner class HttpCallback<R, E>(loadingType: Int = LoadingType.NONE) :
        IteratorCallback<R>(loadingType) {
        override fun onInterceptError(error: Throwable) {
            if (error is HttpException) {
                error.response()?.errorBody().let {
                    if (it == null) Timber.e("onInterceptError -> HttpException errorBody empty")
                    else it.string().let {
                        try {
                            GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                                .create()
                                .fromJson(it, errorResponseClass)?.let {
                                    onHttpResponseError(it)
                                }
                        } catch (e: IOException) {
                            Timber.e(e, "onInterceptError -> parse body failed ${e.message}")
                        } catch (e: JsonSyntaxException) {
                            Timber.e(e, "onError -> parse body failed ${e.message}")
                        }
                    }
                }
            } else onUnhandledError(error)
        }

        protected abstract val errorResponseClass: Class<E>

        @Suppress("UNUSED_PARAMETER")
        protected open fun onHttpResponseError(e: E) {
        }

        @Suppress("UNUSED_PARAMETER")
        protected open fun onUnhandledError(e: Throwable) {
        }
    }

    abstract inner class ApiCallback<R>(loadingType: Int = LoadingType.NONE) :
        HttpCallback<R, Response>(loadingType) where R : Response {
        override val errorResponseClass = Response::class.java

        final override fun onSuccess(result: R) {
            if (result.isSuccess()) {
                onApiResponseSuccess(result)
            } else {
                onApiResponseFailed(result)
            }
        }

        protected abstract fun onApiResponseSuccess(response: R)

        protected open fun onApiResponseFailed(response: R) {
        }
    }

    inner class SilentCallback<MODEL> :
        BaseCallback<MODEL>() {
        override fun onSuccess(result: MODEL) {}
        override fun onError(error: Throwable) {}
    }
}