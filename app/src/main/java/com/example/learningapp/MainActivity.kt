package com.example.learningapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.learningapp.net.ContinuousPingManager
import com.example.learningapp.net.PingResultListener
import com.example.learningapp.net.PingTask
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.learningapp.net.PingResult
import com.example.learningapp.net.PingResultAdapter

class MainActivity : AppCompatActivity(), PingResultListener {

    private lateinit var pingButton: Button
    private lateinit var ipTextBox: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PingResultAdapter

    private val pingResults = mutableListOf<PingResult>()
    private var continuousPingManager: ContinuousPingManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pingButton = findViewById(R.id.pingButton)
        ipTextBox = findViewById(R.id.textInputEditText)
        recyclerView = findViewById(R.id.pingRecyclerView)

        adapter = PingResultAdapter(pingResults)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        pingButton.setOnClickListener {
            val ip = ipTextBox.text.toString()
            if(continuousPingManager == null) {
                // start continuous ping
                val pingTask = PingTask(this)
                continuousPingManager = ContinuousPingManager(ip, pingTask, this)
                continuousPingManager?.start(1000) // ping every 1 second
                pingButton.text = "Stop Ping"
            } else {
                // stop continuous ping
                continuousPingManager?.stop()
                continuousPingManager = null
                pingButton.text = "Start Ping"
            }

        }
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onRestart() {
        super.onRestart()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        continuousPingManager?.stop()
    }

    override fun onPingSuccess(ip: String, timeMs: Long) {
        runOnUiThread {
            adapter.addResult(PingResult(ip, timeMs, null))
            recyclerView.scrollToPosition(adapter.itemCount - 1)
        }
    }

    override fun onPingFailure(ip: String, error: String) {
       runOnUiThread {
           adapter.addResult(PingResult(ip, null, error))
           recyclerView.scrollToPosition(adapter.itemCount - 1)
       }
    }
}