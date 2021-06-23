package com.willendless.exer11

import android.app.Service
import android.content.ContentValues
import android.content.Intent
import android.os.Binder
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.widget.Toast
import kotlin.concurrent.thread

class MyService : Service() {

    @Volatile
    private var running = false

    private lateinit var handler: Thread

    val numList = ArrayList<Long>()

    inner class MyBinder : Binder() {
        fun getNumList() = numList
    }

    override fun onBind(intent: Intent): IBinder = MyBinder()

    override fun onCreate() {
        super.onCreate()

        // 数据库建表
        MyDBHandler(this, "num", 1).writableDatabase
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("service", "start")
        running = true

        handler = thread {
            while (running) {
                Thread.sleep(20 * 1000)

                val num = (0..100000).random()

                numList.add(num.toLong())

                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(this, "生产了一个随机数$num", Toast.LENGTH_SHORT).show()
                }

                MyDBHandler(this, "num", 1).writableDatabase.use { db ->
                    ContentValues().apply {
                        put("num", num)
                        db.insert("num", null, this)
                    }
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        running = false
        handler.join()
        super.onDestroy()
    }

}