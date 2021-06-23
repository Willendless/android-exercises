package com.willendless.exer10

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    fun getRandomString(length: Int) : String {
        val allowedChs = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..length)
            .map { allowedChs.random() }
            .joinToString("")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MyDBHeldper(this, "random_string", 1).writableDatabase

        btn.setOnClickListener {
            thread {
                Log.d("thread", "start working")

                runOnUiThread {
                    textView.text = "数据等待中"
                }

                val randomString = getRandomString((1..100).random())

                Thread.sleep(10 * 1000)

                // 保存到sqlite
                MyDBHeldper(this, "random_string", 1).writableDatabase.use { db ->
                    ContentValues().apply {
                        put("random_string", randomString)
                        db.insert("random_string", null, this)
                    }
                }

                runOnUiThread {
                    textView.text = randomString
                }
            }
        }
    }
}