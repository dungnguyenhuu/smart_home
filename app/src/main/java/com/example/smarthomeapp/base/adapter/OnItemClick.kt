package com.example.smarthomeapp.base.adapter

import android.view.View

interface OnItemClick<T> {
    fun onItemClick(position: Int, view: View?, t: T?)
}