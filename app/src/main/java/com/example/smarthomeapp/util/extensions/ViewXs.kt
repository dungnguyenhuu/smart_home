package com.example.smarthomeapp.util.extensions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

fun <T : ViewDataBinding> ViewGroup.inflateBinding(
    @LayoutRes layoutResId: Int,
    attachedToRoot: Boolean = false,
): T =
    LayoutInflater.from(context).let {
        DataBindingUtil.inflate(it, layoutResId, this, attachedToRoot)
    }

fun <T : ViewDataBinding> LayoutInflater.inflateBinding(
    @LayoutRes layoutResId: Int,
    viewGroup: ViewGroup? = null,
    attachedToRoot: Boolean = false,
): T = DataBindingUtil.inflate(this, layoutResId, viewGroup, attachedToRoot)