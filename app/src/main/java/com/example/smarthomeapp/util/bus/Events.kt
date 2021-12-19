package com.example.smarthomeapp.util.bus

import org.greenrobot.eventbus.EventBus

object Events {

    fun Any.registerEventBus() {
        EventBus.getDefault().apply {
            if (!isRegistered(this@registerEventBus))
                register(this@registerEventBus)
        }
    }

    fun Any.unregisterEventBus() {
        EventBus.getDefault().apply {
            if (isRegistered(this@unregisterEventBus))
                unregister(this@unregisterEventBus)
        }
    }

    fun Any.postAsEvent() {
        EventBus.getDefault().post(this)
    }
}