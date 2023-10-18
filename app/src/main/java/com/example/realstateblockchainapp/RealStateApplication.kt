package com.example.realstateblockchainapp

import android.app.Application
import com.example.realstateblockchainapp.shared.di.appModule
import com.example.realstateblockchainapp.shared.di.dataModule
import com.example.realstateblockchainapp.shared.di.domainModule
import com.example.realstateblockchainapp.shared.di.networkModule
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
            modules(networkModule)
            modules(dataModule)
            modules(domainModule)
        }
    }
}