package com.willendless.exer7

import android.app.Activity
import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.Message
import android.util.Log
import android.widget.Toast
import java.util.*
import kotlin.concurrent.thread

class MyService : Service() {

    private val sendToast = 1

    private lateinit var runner: Thread

    @Volatile
    private var running = false

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("MyService", "create")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("MyService", "start")
        running = true

        runner = thread {
            while (running) {
                Thread.sleep(1 * 1000)
                Handler(Looper.getMainLooper()).post {
                    TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"))
                    Toast.makeText(this, "hello ${Calendar.getInstance().time}",
                        Toast.LENGTH_SHORT).show()
                }
                Log.d("tick", "running: $running")
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        running = false
        runner.join()
        Log.d("MyService", "stop")
    }

}