package com.sunaa.spendmuch.logmessage.domain

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.delay

class AsynLogWorker(appContext: Context, workerParams: WorkerParameters) : CoroutineWorker(appContext,workerParams){
    override suspend fun doWork(): Result {
        Log.d("LogCoroutineWorker", "Logging message every minute")

        // Delay for 1 minute
        delay(6000L) // Delay for 1 minute (60,000 milliseconds)
        Log.d("LogComplete","complete my work")
        return Result.success()
    }

}