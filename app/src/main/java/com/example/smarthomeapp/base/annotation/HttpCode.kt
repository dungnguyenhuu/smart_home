package com.example.smarthomeapp.base.annotation

import androidx.annotation.IntDef

@IntDef(
    HttpCode.OK,
    HttpCode.CREATED,
    HttpCode.UNAUTHORIZED,
    HttpCode.FORBIDDEN,
    HttpCode.NOT_FOUND
)
@Retention(AnnotationRetention.SOURCE)
annotation class HttpCode {

    companion object {
        const val OK = 200
        const val CREATED = 201
        const val UNAUTHORIZED = 401
        const val FORBIDDEN = 403
        const val NOT_FOUND = 404
    }
}