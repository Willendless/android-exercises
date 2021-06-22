package com.willendless.exer6

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView.webViewClient = WebViewClient()

        navView.setCheckedItem(R.id.toSEI)
        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.toSEI -> webView.loadUrl("http://www.sei.ecnu.edu.cn")
                R.id.toED -> webView.loadUrl("http://www.ed.ecnu.edu.cn")
                R.id.toFEM -> webView.loadUrl("http://fem.ecnu.edu.cn")
            }
            true
        }
    }
}