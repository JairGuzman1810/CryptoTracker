package com.app.cryptotracker

import android.app.Application
import com.app.cryptotracker.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

/**
 * CryptoTrackerApp is the main Application class for the CryptoTracker app.
 *
 * This class initializes Koin for dependency injection when the application starts.
 */
class CryptoTrackerApp : Application() {

    /**
     * onCreate is called when the application is starting.
     *
     * This function initializes Koin with the application context, Android logger, and the appModule.
     */
    override fun onCreate() {
        super.onCreate()
        // Start Koin with the appModule.
        startKoin {
            // Provide the Android context to Koin.
            androidContext(this@CryptoTrackerApp)
            // Enable Android logging for Koin.
            androidLogger()
            // Load the appModule.
            modules(appModule)
        }
    }
}