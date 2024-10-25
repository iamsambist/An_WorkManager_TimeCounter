package com.sunaa.spendmuch.timecounter

import javax.inject.Inject

class TimeCounter @Inject constructor(){
    private var startTime: Long = 0L
    private var elapsedTime: Long = 0L

    fun start(initialElapsedTime: Long = 0L) {
        startTime = System.currentTimeMillis()
        elapsedTime = initialElapsedTime
    }

    fun stop(): Long {
        val currentTime = System.currentTimeMillis()
        elapsedTime += (currentTime - startTime)
        return elapsedTime
    }
}