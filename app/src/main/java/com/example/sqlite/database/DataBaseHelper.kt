package com.example.buyersnotebook.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.sqlite.database.DataBaseClass

class DataBaseHelper(context: Context): SQLiteOpenHelper(context, DataBaseClass.DATABASE_NAME, null, DataBaseClass.DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(DataBaseClass.CREATE_DATA_BASE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(DataBaseClass.DELETE_DATA_BASE)
        onCreate(db)

    }

}