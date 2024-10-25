/*
package com.sunaa.spendmuch

// This Worker Factory Provides Single Instance of Worker class

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.work.Configuration
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.sunaa.spendmuch.timecounter.PrefImplementaion
import com.sunaa.spendmuch.workers.timereset.ResetTimeWorker
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class Myapp : Application(), Configuration.Provider {        // Implement Provider Interface

    @Inject
    lateinit var resetTimeWorkerFactory: ResetTimeWorkerFactory

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setMinimumLoggingLevel(Log.DEBUG)
            .setWorkerFactory(resetTimeWorkerFactory)
            .build()
    }

}

class ResetTimeWorkerFactory @Inject constructor(private val localStore: PrefImplementaion) :
    WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? = ResetTimeWorker(localStore, appContext, workerParameters)

}*/
