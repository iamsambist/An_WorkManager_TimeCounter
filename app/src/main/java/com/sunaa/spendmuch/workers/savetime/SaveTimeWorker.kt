package com.sunaa.spendmuch.workers.savetime

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.sunaa.spendmuch.timecounter.PrefImplementaion
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class SaveTimeWorker @AssistedInject constructor(
    @Assisted private val localStore: PrefImplementaion,
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result {
        return try {
            val newTime = inputData.getLong("key", 0)
            localStore.saveNewTime(newTime)
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}