package com.example.smarthomeapp.base.adapter.flex

import androidx.databinding.ViewDataBinding
import com.example.smarthomeapp.base.holder.Holder

fun interface Binder<VIEW : ViewDataBinding, MODEL> {

    fun onCreate(holder: Holder<out MODEL>, view: VIEW) {}

    fun onBind(holder: Holder<out MODEL>, view: VIEW, model: MODEL?)
}