package com.sunaa.spendmuch.logmessage.domain

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class LogWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {
    override fun doWork(): Result {
        Log.d("LogWorker", "Logging message every minute")
        return Result.success()
    }
}