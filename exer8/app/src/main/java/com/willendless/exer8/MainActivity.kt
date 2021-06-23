package com.willendless.exer8

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.io.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 文件写入的模拟
        try {
            val output = openFileOutput("data", Context.MODE_PRIVATE)
            val writer = BufferedWriter(OutputStreamWriter(output))
            writer.use {
                for (i in 1 until 20) {
                    it.write("2020-1-$i,标题$i,事件描述$i\n")
                }
            }
        } catch (e : IOException) {
            e.printStackTrace()
        }

        val logItems = ArrayList<LogItem>()
        try {
            val input = openFileInput("data")
            val reader = BufferedReader(InputStreamReader(input))
            reader.useLines { lines ->
                lines.forEach {
                    val time = it.substringBefore(",")
                    val headAndContent = it.substringAfter(",")
                    logItems.add(
                        LogItem(time,
                            headAndContent.substringBefore(","),
                            headAndContent.substringAfter(",")))
                }
            }
        } catch (e : IOException) {
            e.printStackTrace()
        }

        Log.d("logItems", "$logItems")

        recyclerView.let {
            it.layoutManager = LinearLayoutManager(this)
            it.adapter = LogItemAdapter(logItems, it)
        }
    }
}