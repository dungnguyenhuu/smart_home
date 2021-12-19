package com.example.smarthomeapp.base.scene

import androidx.annotation.IntDef

@IntDef(
    BinderConst.NOT_BINDING
)
@Retention(AnnotationRetention.SOURCE)
annotation class BinderConst {
    companion object {
        const val NOT_BINDING = -1
    }
}