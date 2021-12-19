package com.example.smarthomeapp.util.bindmethods

import android.view.View
import androidx.databinding.BindingAdapter

object CommonBindingAdapter {

    @BindingAdapter("viewCompatSelected")
    @JvmStatic
    fun viewCompatSelected(view: View, selected: Boolean) {
        view.isSelected = selected
    }

    @BindingAdapter("viewCompatVisibility")
    @JvmStatic
    fun viewCompatVisibility(view: View, isVisible: Boolean) {
        view.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

}