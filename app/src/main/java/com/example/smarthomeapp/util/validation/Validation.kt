package com.example.smarthomeapp.util.validation

import android.util.Patterns

fun String.isEmail() = this.matches(Patterns.EMAIL_ADDRESS.toRegex())

fun String.isValidPassword() = this.length in 8..16

fun String.isValidFullName() = this.length >= 6