package com.sunaa.spendmuch.workers.timereset

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import javax.inject.Inject

class WorkSchedular @Inject constructor(context: Context) {

    fun scheduleResetWorker(context: Context) {
        val workRequest = OneTimeWorkRequestBuilder<ResetTimeWorker>().build()
        WorkManager.getInstance(context).enqueueUniqueWork(
            "resetWork",
            ExistingWorkPolicy.KEEP,
            workRequest
        )
    }
}