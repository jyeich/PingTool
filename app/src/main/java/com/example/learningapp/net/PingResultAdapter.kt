package com.example.learningapp.net

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.learningapp.R

class PingResultAdapter (
    private val results: MutableList<PingResult>
) : RecyclerView.Adapter<PingResultAdapter.PingViewHolder>() {

    inner class PingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val resultText: TextView = itemView.findViewById(R.id.pingResultText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PingViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_ping_result, parent, false)
        return PingViewHolder(view)
    }

    override fun onBindViewHolder(holder: PingViewHolder, position: Int) {
        val pingResult = results[position]
        val text = if (pingResult.error == null) {
            "Ping ${pingResult.ip}: ${pingResult.timeMs}ms"
        } else {
            "Ping ${pingResult.ip} failed: ${pingResult.error}"
        }
        holder.resultText.text = text
    }

    override fun getItemCount(): Int = results.size

    fun addResult(result: PingResult) {
        results.add(result)
        Log.d("PingAdapter", "Added: ${result.ip}, total items: ${results.size}")
        notifyItemInserted(results.size - 1)
    }
    @SuppressLint("NotifyDataSetChanged")
    fun clearResults() {
        results.clear()
        notifyDataSetChanged()
    }
}