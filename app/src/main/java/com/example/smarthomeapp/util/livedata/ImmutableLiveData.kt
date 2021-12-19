package com.example.smarthomeapp.util.livedata

import androidx.lifecycle.LiveData
import java.util.*

class ImmutableLiveData<T>(value: T) : LiveData<T>(value) {
    init {
        Objects.requireNonNull(value)
    }

}