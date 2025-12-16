package com.example.learningapp.net

data class PingResult(
    val ip: String,
    val timeMs: Long? = null,
    val error: String? = null
)
