package com.example.smarthomeapp.util.list

import android.graphics.drawable.Drawable
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.smarthomeapp.base.adapter.flex.BaseFlexAdapter
import com.example.smarthomeapp.base.annotation.LoadingType

interface IListController {
    /**
     * indicate loading state
     *
     * @return observable
     */
    val isLoading: LiveData<Boolean>

    /**
     * indicate loading more state
     *
     * @return observable
     */
    val isLoadingMore: LiveData<Boolean>

    /**
     * indicate refreshing state
     *
     * @return observable
     */
    val isRefreshing: LiveData<Boolean>

    /**
     * indicate enable or disable refresh-action
     *
     * @return observable
     */
    val isRefreshEnabled: LiveData<Boolean>

    /**
     * indiate empty data state
     *
     * @return observable, null if disable empty-data
     */
    val isEmptyData: LiveData<Boolean>

    /**
     * indiate empty message
     *
     * @return observable, null if disable empty-data
     */
    fun observableEmptyMessage(): LiveData<String?>

    /**
     * indiate empty drawable
     *
     * @return observable, null if disable empty-data
     */
    fun observableEmptyDrawable(): LiveData<Drawable?>

    /**
     * provide adapter for recycler view
     *
     * @return adapter
     */
    fun recyclerViewAdapter(): BaseFlexAdapter<*>?

    /**
     * provide loadmore listener for recycler view
     */
    fun onRecyclerLoadmore(totalItemsCount: Int, view: RecyclerView)

    /**
     * trigger when user swipe to refresh
     */
    fun onSwipeRefresh()

    /**
     * trigger when user click on empty icon or message to reload
     */
    fun onRequestTryAgain()

    /**
     * Call when a loading-action starts
     *
     * @return true if handle loading, false otherwise
     */
    fun notifyLoaderStarted(@LoadingType loadingType: Int): Boolean

    /**
     * Call when a loading-action finished
     *
     * @return true if handle loading, false otherwise
     */
    fun notifyLoaderFinished(@LoadingType loadingType: Int): Boolean
}