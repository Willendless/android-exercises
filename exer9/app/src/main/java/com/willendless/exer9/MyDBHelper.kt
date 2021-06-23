package com.willendless.exer9

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.lang.StringBuilder

class MyDBHelper(val columns: Array<String>?, context: Context, name: String, version: Int) :
    SQLiteOpenHelper(context, name, null, version) {

    private val createGame = "create table Game (" +
            " id integer primary key autoincrement," +
            " name text," +
            " info text," +
            " year integer," +
            " type text," +
            " file_path text," +
            " img_name text)"

    private val createCollection = "create table Collection (" +
            " id integer primary key autoincrement," +
            " game_name text)"

    override fun onCreate(db: SQLiteDatabase?) {
        val s = StringBuilder("create table contacts (")
        val it = columns!!.iterator()

        s.append("${it.next()} text")

        for (c in it) {
            s.append(",$c text")
        }
        s.append(")")

        Log.d("create table", "$s")
        db?.execSQL(s.toString())
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }
}