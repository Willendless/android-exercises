package com.willendless.exer11

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var myBinder: MyService.MyBinder

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            myBinder = service as MyService.MyBinder
        }

        override fun onServiceDisconnected(name: ComponentName?) {
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startBtn.setOnClickListener {
            val intent = Intent(this, MyService::class.java)

            bindService(intent, connection, Context.BIND_AUTO_CREATE)
            startService(intent)
        }

        stopBtn.setOnClickListener {
            var sum: Long = 0
            val intent = Intent(this, MyService::class.java)

            // 基于Binder的通信
            Toast.makeText(this, "从Binder处获取的所有随机数之和为${myBinder.getNumList().sum()}", Toast.LENGTH_SHORT).show()
            unbindService(connection)
            stopService(intent)

            // 基于共享数据库的通信
            MyDBHandler(this, "num", 1).readableDatabase.use { db ->
                db.query("num", arrayOf("num"), null, null, null, null, null, null).use {
                    if (it.moveToFirst()) {
                        do {
                            sum += it.getLong(it.getColumnIndex("num"))
                        } while (it.moveToNext())
                    }
                }
            }
            textView.text = "所有生成的随机数的和是: $sum"

        }

    }
}