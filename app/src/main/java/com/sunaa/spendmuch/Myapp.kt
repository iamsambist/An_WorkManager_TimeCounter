package com.sunaa.spendmuch


import android.app.Application
import android.content.Context
import android.util.Log
import androidx.work.Configuration
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.sunaa.spendmuch.timecounter.PrefImplementaion
import com.sunaa.spendmuch.workers.savetime.SaveTimeWorker
import com.sunaa.spendmuch.workers.timereset.ResetTimeWorker
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

// look SingleMyApp inside logMessage to Understand SingleCustom Worker Construction

@HiltAndroidApp
class Myapp : Application(), Configuration.Provider {

    @Inject
    lateinit var resetTimeWorkerFactory: ResetTimeWorkerFactory

    @Inject
    lateinit var saveTimeWorkerFactory: SaveTimeWorkerFactory


    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setMinimumLoggingLevel(Log.DEBUG)
            .setWorkerFactory(
                MyDelegatingWorkerFactory(
                    resetTimeWorkerFactory,
                    saveTimeWorkerFactory
                )
            )
            .build()
    }

}

class MyDelegatingWorkerFactory @Inject constructor(
    private val resetTimeWorkerFactory: ResetTimeWorkerFactory,
    private val saveTimeWorkerFactory: SaveTimeWorkerFactory
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return when (workerClassName) {
            ResetTimeWorker::class.java.name -> resetTimeWorkerFactory.createWorker(
                appContext, workerClassName, workerParameters
            )

            SaveTimeWorker::class.java.name -> saveTimeWorkerFactory.createWorker(
                appContext, workerClassName, workerParameters
            )

            else -> null
        }
    }
}


class ResetTimeWorkerFactory @Inject constructor(private val localStore: PrefImplementaion) :
    WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? = ResetTimeWorker(localStore, appContext, workerParameters)
}

class SaveTimeWorkerFactory @Inject constructor(private val localStore: PrefImplementaion) :
    WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? = SaveTimeWorker(localStore, appContext, workerParameters)

}