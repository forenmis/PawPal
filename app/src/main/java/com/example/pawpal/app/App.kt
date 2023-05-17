package com.example.pawpal.app

import android.app.Application
import android.content.Context
import com.example.pawpal.app.app_preference.AppPreference
import com.example.pawpal.data.database.DatabaseManager
import com.example.pawpal.data.network.NetworkManager

class App : Application() {

    lateinit var appPreference: AppPreference
    lateinit var networkManager: NetworkManager
    lateinit var databaseManager: DatabaseManager

    override fun onCreate() {
        super.onCreate()
        appPreference = AppPreference(applicationContext)
        networkManager = NetworkManager(applicationContext)
        databaseManager = DatabaseManager(this)
    }

    companion object {
        fun getInstance(context: Context): App = context.applicationContext as App
    }
}