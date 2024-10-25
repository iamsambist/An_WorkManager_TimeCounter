package com.sunaa.spendmuch.ui.view

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sunaa.spendmuch.timecounter.PrefImplementaion
import com.sunaa.spendmuch.timecounter.TimeCounter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TimerViewModel @Inject constructor(
    val localStore: PrefImplementaion,
    val timeCounter: TimeCounter
) : ViewModel() {

    private val _flowTime = MutableStateFlow(0L)
    val flowTime: StateFlow<Long> = _flowTime

    init {
        viewModelScope.launch {
            localStore.getUserTime().collect { longValue ->
                _flowTime.value = longValue
                Log.i("time",_flowTime.value.toString())

            }
        }
    }

    fun clearTimeHistory(){
        viewModelScope.launch {
            localStore.clearSavedTime()
        }
    }

    fun startCount() {
        timeCounter.start()
    }

    fun getNewTime() : Long{
        val currentTimeSpent = timeCounter.stop()
        val previousTime = flowTime.value
        return currentTimeSpent + previousTime
    }

    fun stopCounter() {
        val currentTimeSpent = timeCounter.stop()
        val previousTime = flowTime.value
        viewModelScope.launch {
            localStore.saveNewTime(currentTimeSpent + previousTime)
        }
        Log.i("times",(currentTimeSpent + previousTime ).toString())
    }
}