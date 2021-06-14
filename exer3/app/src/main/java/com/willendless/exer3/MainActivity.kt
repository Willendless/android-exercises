package com.willendless.exer3

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), AdapterView.OnItemClickListener {

    private val contactsList = ArrayList<String>()
    private val contactIdList = ArrayList<String>()
    private lateinit var adapter: ArrayAdapter<String>

    private fun permissionGranted(permission: String): Boolean =
        ContextCompat.checkSelfPermission(this, permission) ==
                PackageManager.PERMISSION_GRANTED

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contactsList)
        contactsListView.adapter = adapter

        if (permissionGranted(Manifest.permission.READ_CONTACTS)) {
            readContacts()
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_CONTACTS), 1)
        }

        contactsListView.onItemClickListener = this
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        readContacts()
                } else {
                    Toast.makeText(this, "你拒绝了通讯录读取的授权", Toast.LENGTH_SHORT).show()
                }
            }
            2 -> {
                if (!(grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    Toast.makeText(this, "你拒绝了拨打电话的授权", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun readContacts() {
        contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null, null, null, null)?.apply {
            while (moveToNext()) {
                val name = getString(getColumnIndex(
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
                ))
                val number = getString(getColumnIndex(
                    ContactsContract.CommonDataKinds.Phone.NUMBER
                ))
                val contactId = getString(getColumnIndex(
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                ))
                contactsList.add("$name\n$number")
                contactIdList.add(contactId)
            }
            adapter.notifyDataSetChanged()
            close()
        }
    }

    private fun call(number: String) {
        try {
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel: $number")
            startActivity(intent)
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val s = contactsList[position]
        val name = s.substringBefore('\n')
        val number = s.substringAfter('\n')
        val contactId = contactIdList[position]

        val email = contentResolver.query(
            ContactsContract.CommonDataKinds.Email.CONTENT_URI,
            arrayOf(ContactsContract.CommonDataKinds.Email.ADDRESS),
            ContactsContract.CommonDataKinds.Email.CONTACT_ID + "=?",
            arrayOf(contactId),
            null
            )?.use { it -> String
                if (it.moveToFirst()) {
                    it.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS))
                } else {
                    ""
                }
            }

        val activity = this
        AlertDialog.Builder(this).apply {
            setTitle("帮助")
            setMessage("姓名: $name\n电话号码: $number\n邮箱: $email")
            setNegativeButton("返回") { dialogInterface: DialogInterface, _: Int ->
                dialogInterface.dismiss()
            }
            setPositiveButton("拨打") { _: DialogInterface, _: Int ->
                if (permissionGranted(Manifest.permission.CALL_PHONE)) {
                    call(number)
                } else {
                    ActivityCompat.requestPermissions(activity,
                        arrayOf(Manifest.permission.CALL_PHONE), 2)
                }
            }
            show()
        }
    }
}