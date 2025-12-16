package com.example.learningapp.net

import java.io.IOException
import java.net.InetAddress
import kotlin.concurrent.thread


class PingTask(private val listener: PingResultListener) {

    fun ping(ip: String) {
        thread{
            try {
                val address = InetAddress.getByName(ip)
                val start = System.currentTimeMillis()
                val reachable = address.isReachable(3000) // 3 second timeout
                val end = System.currentTimeMillis()

                if(reachable) {
                    listener.onPingSuccess(ip, end - start)
                }
                else {
                    listener.onPingFailure(ip, "Host unreachable")
                }
            } catch (e: IOException) {
                listener.onPingFailure(ip, e.message?: "Unknown error")
            }
        }
    }

}