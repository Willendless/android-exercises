package com.willendless.exer9

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main2.*
import java.lang.StringBuilder

class MainActivity2 : AppCompatActivity() {

    companion object {
        fun actionStart(context: Context) {
            val intent = Intent(context, MainActivity2::class.java)
            context.startActivity(intent)
        }
    }

    private val nameHead = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val contactList = ArrayList<String>()

        MyDBHelper(null, this, "contacts", 1).readableDatabase.use { db ->
            db.query("contacts", null, null,
                null, null, null, nameHead).use {
                    Log.d("query count", "${it.count}")
                    while (it.moveToNext()) {
                        val s = StringBuilder()
                        for (c in it.columnNames) {
                            val content = it.getString(it.getColumnIndex(c))
                            if (content != null)
                                s.append("$c: $content\n")
                        }
                        contactList.add(s.toString())
                    }
            }
        }

        listView.adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contactList)
    }
}