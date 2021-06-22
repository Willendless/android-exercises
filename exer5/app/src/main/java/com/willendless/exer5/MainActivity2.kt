package com.willendless.exer5

import android.app.NotificationManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        // 已收到通知，取消通知的显示
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.cancel(1)

        val key1 = intent.getIntExtra("key1", 0)
        val key2 = intent.getStringExtra("key2")

        key1Text.text = "key1: $key1"
        key2Text.text = "key2: $key2"
    }
}