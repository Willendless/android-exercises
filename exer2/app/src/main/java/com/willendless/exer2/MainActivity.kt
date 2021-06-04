package com.willendless.exer2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        request.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        thread {
            var connection: HttpURLConnection? = null
            try {
                val response = StringBuilder()
                val url = URL("http://115.29.231.93:8080/CkeditorTest/AndroidTest?userId=250&style=json")
                connection = url.openConnection() as HttpURLConnection
                connection.connectTimeout = 8000
                connection.readTimeout = 8000
                val input = connection.inputStream
                val reader = BufferedReader(InputStreamReader(input))
                reader.use {
                    reader.forEachLine {
                        response.append(it)
                    }
                }
                showResponse(response.toString())
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                connection?.disconnect()
            }
        }
    }

    private fun showResponse(response: String) {
        try {
            val jsonObject = JSONObject(response)
            val returnCode = jsonObject.getInt("returnCode")
            runOnUiThread {
                return_code.setText(returnCode.toString())
                if (returnCode >= 0) {
                    val returnValue = jsonObject.getString("returnValue")
                    return_value.setText(returnValue.toString())
                } else {
                    val returnMsg = jsonObject.getString("returnMsg")
                    return_msg.setText(returnMsg.toString())
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}