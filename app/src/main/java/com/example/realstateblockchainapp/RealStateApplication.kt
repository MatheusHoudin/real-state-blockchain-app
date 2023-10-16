package com.example.realstateblockchainapp

import android.app.Application
import com.example.realstateblockchainapp.shared.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class RealStateApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger()
            androidContext(this@RealStateApplication)
            modules(appModule)
        }
    }
}