package com.willendless.exer5

import android.app.NotificationChannel
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.app.NotificationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val broadcastReceiver = MyBroadcastReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 注册通知channel
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("normal", "Normal",
                NotificationManager.IMPORTANCE_HIGH)
            manager.createNotificationChannel(channel)
        }

        // 动态注册广播的接收者，以能够接收隐式系统广播
        val intentFilter = IntentFilter()
        intentFilter.addAction("com.willendless.exer5.MY_BROADCAST")
        registerReceiver(broadcastReceiver, intentFilter)

        button.setOnClickListener {
            val intent = Intent("com.willendless.exer5.MY_BROADCAST")
            intent.putExtra("key1", 100)
            intent.putExtra("key2", "hello")
            // 隐式广播无须指明接收方，否则需要：intent.setPackage()
            sendOrderedBroadcast(intent, null)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(broadcastReceiver)
    }
}