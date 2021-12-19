package com.example.smarthomeapp.base.network

import java.io.IOException

class NoConnectivityException(message: String?) : IOException(message) {

    constructor() : this("No Internet Connection")
}