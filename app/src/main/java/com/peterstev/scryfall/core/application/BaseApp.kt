package com.peterstev.scryfall.core.application

import android.app.Application
import android.os.Debug
import android.os.StrictMode
import com.peterstev.safebodachallengepeterslight.BuildConfig
import com.peterstev.scryfall.presentation.TIMBER_START
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class BaseApp : Application() {

    override fun onCreate() {

        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build()
            )

            StrictMode.setVmPolicy(
                StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build()
            )
        }

        Debug.startMethodTracing("tracer")
        super.onCreate()

    }
}