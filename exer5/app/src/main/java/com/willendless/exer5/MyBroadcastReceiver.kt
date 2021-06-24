package com.willendless.exer5

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.app.NotificationCompat
import android.app.NotificationManager
import android.app.PendingIntent

class MyBroadcastReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        val key1 = intent?.getIntExtra("key1", 0)
        val key2 = intent?.getStringExtra("key2")

        // 构造pending intent
        val notificationIntent = Intent(context, MainActivity2::class.java)
        notificationIntent.putExtra("key1", key1)
        notificationIntent.putExtra("key2", key2)
        val pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val notification = NotificationCompat.Builder(context!!, "normal")
            .setContentTitle("这是一条通知")
            .setContentText("点击可以跳转到Activity B")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(1, notification)

        Toast.makeText(context, "received broadcast in MyBroadcastReceiver\nkey1: $key1\nkey2: $key2",
            Toast.LENGTH_SHORT).show()
    }
}