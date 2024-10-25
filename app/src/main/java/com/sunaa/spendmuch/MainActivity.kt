package com.sunaa.spendmuch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.sunaa.spendmuch.ui.theme.SpendMuchTheme
import com.sunaa.spendmuch.ui.view.ScreenTimerView
import com.sunaa.spendmuch.ui.view.TimerViewModel
import com.sunaa.spendmuch.workers.savetime.SaveTimeWorker
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var timerViewModel: TimerViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        timerViewModel = ViewModelProvider(this).get(TimerViewModel::class.java)
        timerViewModel.startCount()

        setContent {
            SpendMuchTheme {
                ScreenTimerView(timerViewModel)
            }
        }
    }

/*    override fun onStop() {
        timerViewModel.stopCounter()
        super.onStop()
    }*/

    override fun onStop() {
        val newTime = timerViewModel.getNewTime()

        val saveRequest : OneTimeWorkRequest = OneTimeWorkRequestBuilder<SaveTimeWorker>()
            .setInputData(workDataOf("key" to newTime)).build()
        WorkManager.getInstance(this).enqueueUniqueWork(
            "savetime",
            ExistingWorkPolicy.REPLACE, // Use REPLACE to test worker
            saveRequest
        )
        super.onStop()
    }
}

