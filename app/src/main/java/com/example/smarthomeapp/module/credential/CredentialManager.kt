package com.example.smarthomeapp.module.credential

interface CredentialManager {

    fun saveAuthToken(token: String)

    fun getAuthToken(): String?

}