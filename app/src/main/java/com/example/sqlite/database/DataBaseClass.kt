package com.example.sqlite.database

import android.provider.BaseColumns

object DataBaseClass {
    const val TABLE_NAME = "notebook"
    const val COLUMN_NAME_NUMBER = "number"
    const val COLUMN_NAME_NAME_PEOPLE = "name"
    const val COLUMN_NAME_ADDRESS = "address"
    const val COLUMN_NAME_WORK = "work"
    const val COLUMN_NAME_IMAGE_URI = "uri"
    const val COLUMN_NAME_TIME = "time"

    const val DATABASE_VERSION = 2
    const val DATABASE_NAME = "DataBaseBuyersNotebook.db"

    const val CREATE_DATA_BASE =
        "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," + "$COLUMN_NAME_NUMBER TEXT," + "$COLUMN_NAME_NAME_PEOPLE TEXT," + "$COLUMN_NAME_ADDRESS TEXT," + "$COLUMN_NAME_WORK TEXT," + "$COLUMN_NAME_IMAGE_URI TEXT," + "$COLUMN_NAME_TIME TEXT)"

    const val DELETE_DATA_BASE = "DROP TABLE IF EXISTS $TABLE_NAME"

}