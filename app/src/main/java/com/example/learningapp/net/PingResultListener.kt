package com.example.learningapp.net

interface PingResultListener {
    fun onPingSuccess(ip: String, timeMs: Long)
    fun onPingFailure(ip: String, error: String)
}