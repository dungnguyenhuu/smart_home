package com.example.smarthomeapp.data.pojo.notification

import java.io.Serializable

data class Notification(
    val id: Int,
    val title: String,
    val description: String
) : Serializable