package com.sunaa.spendmuch.ui.view

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.sunaa.spendmuch.workers.timereset.ResetTimeWorker
import java.util.concurrent.TimeUnit

@Composable
fun ScreenTimerView(
    timerViewModel: TimerViewModel
) {
    val context = LocalContext.current
    val previouslySavedTime by timerViewModel.flowTime.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "${previouslySavedTime / 1000} seconds")
        Button(
            onClick = {
                timerViewModel.clearTimeHistory()
            }, modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) { Text("CLEAR TIME HISTORY") }
        Button(
            onClick = {
                shedulePeriodicWork(context)
            }, modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) { Text("PERFORM WORK REQUEST") }


    }
}

fun scheduleWork(context: Context) {

    val resetTimeRequest: OneTimeWorkRequest = OneTimeWorkRequestBuilder<ResetTimeWorker>()
        .build()

    WorkManager.getInstance(context).enqueueUniqueWork(
        "ResetTimeWork",
        ExistingWorkPolicy.REPLACE, // Use REPLACE to test worker
        resetTimeRequest
    )


}

fun shedulePeriodicWork(context: Context) {
    val periodicWorkRequest =
        PeriodicWorkRequestBuilder<ResetTimeWorker>(15, TimeUnit.MINUTES).build()
    WorkManager.getInstance(context).enqueueUniquePeriodicWork("reset_time_Every_15",
        ExistingPeriodicWorkPolicy.REPLACE,periodicWorkRequest)
}
