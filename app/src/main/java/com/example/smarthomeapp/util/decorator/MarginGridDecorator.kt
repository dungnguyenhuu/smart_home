package com.example.smarthomeapp.util.decorator

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class MarginGridDecorator(
    private val verticalMargin: Int,
    private val horizontalMargin: Int,
    private val firstItemOffset: Int,
    private val lastItemOffset: Int
) : ItemDecoration() {
    private var spanCount = 0
    private var lastItemCount = 0
    private var lastRowStartIndex = 0
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        syncState(parent, state)
        outRect.apply {
            val childPosition = parent.getChildAdapterPosition(view)
            if (isFirstRow(childPosition)) {
                top = firstItemOffset
                bottom = verticalMargin
            } else if (isLastRow(childPosition)) {
                top = verticalMargin
                bottom = lastItemOffset
            } else {
                top = verticalMargin
                bottom = verticalMargin
            }
            left = horizontalMargin
            right = horizontalMargin
        }
    }

    private fun syncState(parent: RecyclerView, state: RecyclerView.State) {
        if (lastItemCount != state.itemCount) {
            lastItemCount = state.itemCount
            if (lastItemCount == 0) {
                lastRowStartIndex = -1
                spanCount = 0
            } else (parent.layoutManager as? GridLayoutManager).let {
                if (it != null) {
                    spanCount = it.spanCount
                    lastRowStartIndex = if (state.itemCount % spanCount == 0) {
                        state.itemCount - spanCount
                    } else {
                        state.itemCount / spanCount * spanCount
                    }
                } else {
                    spanCount = 0
                    lastRowStartIndex = -1
                }
            }
        }
    }

    private fun isFirstRow(position: Int) = position < spanCount

    private fun isLastRow(position: Int) = position >= lastRowStartIndex

    companion object {

        @JvmStatic
        fun newInstance(
            vertical: Float,
            horizontal: Float,
            firstItemOffset: Float,
            lastItemOffset: Float
        ): MarginGridDecorator {
            return MarginGridDecorator(
                vertical.toInt(),
                horizontal.toInt(),
                firstItemOffset.toInt(),
                lastItemOffset.toInt()
            )
        }
    }

}