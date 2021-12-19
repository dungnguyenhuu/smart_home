package com.example.smarthomeapp.util.list

import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.recyclerview.widget.RecyclerView
import com.example.smarthomeapp.base.adapter.OnDataChangedListener
import com.example.smarthomeapp.base.adapter.flex.BaseFlexAdapter
import com.example.smarthomeapp.base.annotation.LoadingType
import com.example.smarthomeapp.util.extensions.ResourceXs
import com.example.smarthomeapp.util.extensions.mediator
import com.example.smarthomeapp.util.livedata.ImmutableLiveData

@Suppress("unused")
class ListController(
    adapter: BaseFlexAdapter<*>?,
    enableEmptyState: Boolean,
    /**
     * Delegate events
     */
    private var callback: Listener?,
) :
    IListController {
    /**
     * Indicate list-content is loading data. It may be init-load, loadmore, refresh-load. Should not use to indicate show or hide indicator
     */
    private val liveInternalLoading: MutableLiveData<Boolean> = MutableLiveData(false)

    /**
     * Indicate swipe refresh layout state, true if refreshing, false otherwise
     */
    private val liveInternalRefreshing: MutableLiveData<Boolean> =
        MutableLiveData(false)

    /**
     * Indicate swipe-to-refresh feature is enable or not
     */
    private val liveInternalRefreshEnabled: MutableLiveData<Boolean> =
        MutableLiveData(false)

    /**
     * Indicate list content is empty or not. Should not use to judge empty message and drawable show or hide
     */
    private val liveInternalEmptyState: MutableLiveData<Boolean> =
        MutableLiveData(true)

    /**
     * Provide empty drawable
     */
    private val liveEmptyDrawable: MutableLiveData<Drawable?> = MutableLiveData(null)

    /**
     * Provide empty message
     */
    private val liveEmptyMessage: MutableLiveData<String?> = MutableLiveData(null)
    private var bindingAdapter: BaseFlexAdapter<*>?

    /**
     * Indicate empty-state of list-content after combining state. It's @[Transformations.distinctUntilChanged]
     */
    private val liveEmptyState: LiveData<Boolean>

    /**
     * Indicate loading-state of list-content after combining state. Use to show blocking-loading-indicator. It's @[Transformations.distinctUntilChanged]
     * <br></br>Can be change combining-method by override @[ListController.combineLoadingState]
     */
    override val isLoading: LiveData<Boolean>

    /**
     * Indicate loadmore-state of list-content after combining state. Use to show blocking-loading-indicator. It's @[Transformations.distinctUntilChanged]
     * <br></br>Can be change combining-method by override @[ListController.combineLoadmoreState]
     */
    private val liveIsLoadmore: LiveData<Boolean>

    override val isLoadingMore: LiveData<Boolean>
        get() = liveIsLoadmore

    override fun observableEmptyMessage(): LiveData<String?> {
        return liveEmptyMessage
    }

    override fun observableEmptyDrawable(): LiveData<Drawable?> {
        return liveEmptyDrawable
    }

    override val isRefreshing: LiveData<Boolean>
        get() = liveInternalRefreshing

    override val isRefreshEnabled: LiveData<Boolean>
        get() = liveInternalRefreshEnabled

    override val isEmptyData: LiveData<Boolean>
        get() = liveEmptyState

    override fun recyclerViewAdapter(): BaseFlexAdapter<*>? {
        return bindingAdapter
    }

    override fun onRecyclerLoadmore(totalItemsCount: Int, view: RecyclerView) {
        callback?.onContentLoadmore(totalItemsCount, view)
    }

    override fun onSwipeRefresh() {
        bindingAdapter?.clear()
        liveInternalRefreshing.value = true
        callback?.onContentRefresh()
    }

    override fun onRequestTryAgain() {
        callback?.onRequestTryAgain()
    }

    override fun notifyLoaderStarted(loadingType: Int): Boolean {
        if (loadingType != LoadingType.BLOCKING) {
            liveInternalLoading.value = true
            return true
        }
        return false
    }

    override fun notifyLoaderFinished(loadingType: Int): Boolean {
        if (loadingType != LoadingType.BLOCKING) {
            liveInternalLoading.value = false
            liveInternalRefreshing.value = false
            return true
        }
        return false
    }

    protected fun combineLoadmoreState(): Boolean {
        return (liveInternalLoading.value!!
                && !liveInternalRefreshing.value!!
                && !liveInternalEmptyState.value!!)
    }

    protected fun combineLoadingState(): Boolean {
        return (liveInternalLoading.value!!
                && !liveInternalRefreshing.value!!
                && liveInternalEmptyState.value!!)
    }

    protected fun combineEmptyState(): Boolean {
        return (!liveInternalRefreshing.value!!
                && !liveInternalLoading.value!!
                && liveInternalEmptyState.value!!)
    }

    fun setRefreshEnable(enable: Boolean) {
        if (enable != liveInternalRefreshEnabled.value) {
            liveInternalRefreshEnabled.value = enable
        }
    }

    fun setEmptyDrawable(drawable: Drawable?) {
        liveEmptyDrawable.value = drawable
    }

    fun setEmptyDrawable(@DrawableRes drawableResId: Int) {
        liveEmptyDrawable.value = ResourceXs.getDrawable(drawableResId)
    }

    fun setEmptyMessage(message: String?) {
        liveEmptyMessage.value = message
    }

    fun setEmptyMessage(@StringRes messageResId: Int) {
        liveEmptyMessage.value = ResourceXs.getString(messageResId)
    }

    interface Listener {
        fun onContentLoadmore(
            totalItemsCount: Int,
            view: RecyclerView
        )

        fun onContentRefresh()
        fun onRequestTryAgain()
    }

    init {
        bindingAdapter = adapter
        bindingAdapter?.setDataChangedListener(object : OnDataChangedListener {
            override fun onDataSetEmpty() {
                liveInternalEmptyState.run {
                    if (!value!!) value = true
                }
            }

            override fun onDataSetFilled() {
                liveInternalEmptyState.run {
                    if (this.value!!) value = false
                }
            }
        })
        liveEmptyState = if (enableEmptyState)
            Transformations.distinctUntilChanged(
                mediator(
                    { combineEmptyState() },
                    liveInternalRefreshing,
                    liveInternalLoading,
                    liveInternalEmptyState
                )
            )
        else ImmutableLiveData(false)

        isLoading = Transformations.distinctUntilChanged(
            mediator(
                this::combineLoadingState,
                liveInternalLoading,
                liveInternalRefreshing,
                liveInternalEmptyState
            )
        )
        liveIsLoadmore = bindingAdapter?.let {
            if (it.isSupportedLoadmoreHolder())
                Transformations.distinctUntilChanged(
                    mediator(
                        this::combineLoadmoreState,
                        liveInternalLoading,
                        liveInternalRefreshing,
                        liveInternalEmptyState
                    )
                )
            else null
        } ?: ImmutableLiveData(false)
    }
}