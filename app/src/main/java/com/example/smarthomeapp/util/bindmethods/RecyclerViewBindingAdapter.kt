package com.example.smarthomeapp.util.bindmethods

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.smarthomeapp.util.loadmore.ScrollLoadmoreHandler

object RecyclerViewBindingAdapter {

    @BindingAdapter("rvAdapter")
    @JvmStatic
    fun setRecyclerViewAdapter(
        recyclerView: RecyclerView,
        adapter: RecyclerView.Adapter<*>?
    ) {
        recyclerView.adapter = adapter
    }

    @BindingAdapter(value = ["rvLayoutManager", "rvLoadmoreListener"])
    @JvmStatic
    fun setRecyclerViewLayoutManager(
        recyclerView: RecyclerView,
        layoutManager: RecyclerView.LayoutManager?,
        onLoadmoreListener: ScrollLoadmoreHandler.OnLoadmoreListener?
    ) {
        recyclerView.apply {
            setLayoutManager(layoutManager)
            clearOnScrollListeners()
            if (layoutManager != null && onLoadmoreListener != null) {
                addOnScrollListener(
                    ScrollLoadmoreHandler(
                        layoutManager,
                        onLoadmoreListener
                    )
                )
            }
        }
    }

    @BindingAdapter(value = ["rvDecoration"])
    @JvmStatic
    fun setRecyclerViewDecoration(
        recyclerView: RecyclerView,
        decoration: RecyclerView.ItemDecoration?
    ) {
        decoration?.let {
            recyclerView.run {
                removeItemDecoration(it)
                addItemDecoration(it)
            }
        }
    }

}