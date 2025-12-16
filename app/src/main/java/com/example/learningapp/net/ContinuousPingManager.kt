package com.example.learningapp.net

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class ContinuousPingManager (
    private val ip: String,
    private val pingTask: PingTask,
    private val listener: PingResultListener
){
    private var job: Job? = null

    fun start(intervalMs: Long = 1000L) {
        stop()

        job = CoroutineScope(Dispatchers.IO).launch {
            while (isActive) {
                pingTask.ping(ip)  // callback will notify listener
                delay(intervalMs)
            }
        }
    }

    fun stop() {
        job?.cancel()
        job = null
    }
}