package com.example.smarthomeapp.util.helper

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import org.jetbrains.annotations.Contract

object RecyclerViewLayoutManagerHelper {

    const val VERT = RecyclerView.VERTICAL
    const val HORZ = RecyclerView.HORIZONTAL

    @Contract("!null, !null, !null -> new")
    @JvmStatic
    fun linear(
        context: Context,
        orientation: Int = VERT,
        reverse: Boolean = false
    ): RecyclerView.LayoutManager {
        return LinearLayoutManager(context, orientation, reverse)
    }

    @JvmStatic
    fun flexBox(context: Context) = FlexboxLayoutManager(context).apply {
        flexDirection = FlexDirection.ROW
        justifyContent = JustifyContent.FLEX_START
        alignItems = AlignItems.CENTER
    }

    @Contract("!null, !null, !null ,!null-> new")
    @JvmStatic
    fun grid(
        context: Context,
        spanCount: Int,
        orientation: Int = VERT,
        reverse: Boolean = false
    ): RecyclerView.LayoutManager {
        return GridLayoutManager(context, spanCount, orientation, reverse)
    }

}