package com.example.smarthomeapp.util.loadmore

import androidx.recyclerview.widget.GridLayoutManager
import com.example.smarthomeapp.util.loadmore.HeadItemListener.IFirstVisibleItemFinder
import com.example.smarthomeapp.util.loadmore.LastItemListener.ILastVisibleItemFinder

internal class GridVisibleItemFinder(private val gridLayoutManager: GridLayoutManager) :
    ILastVisibleItemFinder, IFirstVisibleItemFinder {
    override fun findLastVisibleItemPosition(): Int {
        return gridLayoutManager.findLastVisibleItemPosition()
    }

    override fun findFirstVisibleItemPosition(): Int {
        return gridLayoutManager.findFirstVisibleItemPosition()
    }

}