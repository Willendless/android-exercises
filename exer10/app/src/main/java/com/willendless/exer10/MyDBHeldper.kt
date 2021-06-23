package com.willendless.exer10

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDBHeldper(context: Context, name: String, version: Int) :
    SQLiteOpenHelper(context, name, null, version) {

    private val createNumTable = "create table random_string (random_string text)"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(createNumTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }
}