package com.example.smarthomeapp.util.loadmore

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smarthomeapp.util.loadmore.HeadItemListener.IFirstVisibleItemFinder
import com.example.smarthomeapp.util.loadmore.LastItemListener.ILastVisibleItemFinder

internal class LinearVisibleItemFinder(private val linearLayoutManager: LinearLayoutManager) :
    ILastVisibleItemFinder, IFirstVisibleItemFinder {
    override fun findLastVisibleItemPosition(): Int {
        return linearLayoutManager.findLastVisibleItemPosition()
    }

    override fun findFirstVisibleItemPosition(): Int {
        return linearLayoutManager.findFirstVisibleItemPosition()
    }

}