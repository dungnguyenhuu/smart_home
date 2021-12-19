package com.example.smarthomeapp.util.validation

import androidx.annotation.StringRes
import com.example.smarthomeapp.util.AppResources

class InvalidFieldInputException : IllegalArgumentException {

    private val code: Int

    constructor(code: Int, message: String) : super(message) {
        this.code = code
    }

    constructor(code: Int, @StringRes message: Int) : this(code, AppResources.getString(message))

}

object ValidationCode {
    const val ERROR_EMAIL_INVALID = 0x1
    const val ERROR_PASSWORD_INVALID = 0x2
    const val ERROR_NAME_INVALID = 0x3
}
