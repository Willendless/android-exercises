package com.willendless.exer8

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.util.Range
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import java.io.BufferedWriter
import java.io.FileInputStream
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class LogItemAdapter(val context: Context, var logItems: MutableList<LogItem>): RecyclerView.Adapter<LogItemAdapter.LogItemViewHolder>() {
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
                setPositiveButton("删除") { dialogInterface: DialogInterface, i: Int ->
                    val lines = InputStreamReader(context.openFileInput("data")).use {
                        it.readLines()
                    }

                    BufferedWriter(OutputStreamWriter(context.openFileOutput("data", Context.MODE_PRIVATE))).use {
                        for (i in lines.indices) {
                            if (i != position)
                                it.write("${lines[i]}\n")
                        }
                    }

                    logItems.removeAt(position)
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position, logItems.size)
                }
                show()
            }
        }
    }

    override fun getItemCount(): Int = logItems.size
}