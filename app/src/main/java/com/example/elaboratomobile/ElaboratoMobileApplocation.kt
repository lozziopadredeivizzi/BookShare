package com.example.elaboratomobile

import android.app.Application
import com.example.elaboratomobile.ui.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ElaboratoMobileApplocation: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger()
            androidContext(this@ElaboratoMobileApplocation)
            modules(appModule)
        }
    }
}