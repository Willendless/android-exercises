package com.willendless.exer9

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val schemaArray = arrayOf(
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID
    )

    private fun permissionGranted(permission: String): Boolean =
            ContextCompat.checkSelfPermission(this, permission) ==
                    PackageManager.PERMISSION_GRANTED

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadBtn.setOnClickListener {
            if (permissionGranted(Manifest.permission.READ_CONTACTS)) {
                readContacts()
            } else {
                ActivityCompat.
                requestPermissions(this,
                    arrayOf(Manifest.permission.READ_CONTACTS),
                    1)
            }
        }

        showBtn.setOnClickListener {
            MainActivity2.actionStart(this)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    readContacts()
                } else {
                    Toast.makeText(this, "你拒绝了通讯录读取的授权", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun readContacts() {
        contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null, null)?.use {

            Log.d("query count", "${it.count}")

            val columnNames = it.columnNames
            // 第一次读取则创建数据库
            val handler = MyDBHelper(columnNames, this, "contacts", 1).writableDatabase

            // 写入每一条
            while (it.moveToNext()) {
                ContentValues().apply {
                    for (c in columnNames) {
                        put(c, it.getString(it.getColumnIndex(c)))
                    }
                    handler.insert("contacts", null, this)
                }
            }
        }

        Toast.makeText(this, "加载成功", Toast.LENGTH_SHORT).show()
    }

}