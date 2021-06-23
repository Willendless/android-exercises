package com.willendless.exer8

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class LogItemAdapter(val logItems: List<LogItem>, view: RecyclerView): RecyclerView.Adapter<LogItemAdapter.LogItemViewHolder>() {
    inner class LogItemViewHolder(view: View, val context: Context): RecyclerView.ViewHolder(view) {
        val timeText: TextView = view.findViewById(R.id.log_time)
        val headText: TextView = view.findViewById(R.id.log_head)
        var content = ""
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.log_item, parent, false)
        return LogItemViewHolder(view, parent.context)
    }

    override fun onBindViewHolder(holder: LogItemViewHolder, position: Int) {
        val log = logItems[position]
        holder.timeText.text = log.time
        holder.headText.text = log.head
        holder.content = log.content
        holder.itemView.setOnClickListener {
            AlertDialog.Builder(holder.context).apply {
                setTitle("日志内容")
                setMessage(holder.content)
                setNegativeButton("返回") { dialogInterface: DialogInterface, i: Int ->
                    dialogInterface.dismiss()
                }
                show()
            }
        }
    }

    override fun getItemCount(): Int = logItems.size
}