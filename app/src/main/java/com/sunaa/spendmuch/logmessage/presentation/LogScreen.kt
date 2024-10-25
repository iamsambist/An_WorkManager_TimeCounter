package com.sunaa.spendmuch.logmessage.presentation

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.sunaa.spendmuch.logmessage.domain.AsynLogWorker
import com.sunaa.spendmuch.logmessage.domain.LogWorker
import java.util.concurrent.TimeUnit

@Composable
fun ScreenLogView() {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { scheduleWork(context)},
            colors = ButtonDefaults.buttonColors(Color.Gray),
            modifier = Modifier.fillMaxWidth()
        ) { Text(text = "Start Work", color = Color.Black) }
        Spacer(Modifier.height(20.dp))

        Button(
            onClick = { cancelWork(context)},
            colors = ButtonDefaults.buttonColors(Color.Gray),
            modifier = Modifier.fillMaxWidth()
        ) { Text(text = "Stop Work", color = Color.Black) }
    }
}
fun scheduleWork(context: Context) {
    val workRequest = OneTimeWorkRequestBuilder<LogWorker>().build()
    WorkManager.getInstance(context).enqueueUniqueWork(
        "LogWork",
        ExistingWorkPolicy.KEEP,
        workRequest
    )
}
fun cancelWork(context: Context) {
    WorkManager.getInstance(context).cancelUniqueWork("LogWork")
}

