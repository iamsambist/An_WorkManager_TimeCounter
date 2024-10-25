package com.sunaa.spendmuch.workers.timereset

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.sunaa.spendmuch.timecounter.PrefImplementaion
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class ResetTimeWorker @AssistedInject constructor(
   @Assisted private val localStore : PrefImplementaion,
   @Assisted appContext: Context,
   @Assisted params: WorkerParameters,
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        return try {
            localStore.clearSavedTime()
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}
