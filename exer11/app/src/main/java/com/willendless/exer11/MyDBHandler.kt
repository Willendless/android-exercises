package com.willendless.exer11

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDBHandler(context: Context, name: String, version: Int) :
    SQLiteOpenHelper(context, name, null, version) {

    private val createNumTable = "create table num (num integer)"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(createNumTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }
}