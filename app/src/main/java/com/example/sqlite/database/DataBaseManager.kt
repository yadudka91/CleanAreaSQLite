package com.example.sqlite.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import com.example.buyersnotebook.database.DataBaseHelper
import com.example.sqlite.database.DataBaseClass

class DataBaseManager(context: Context) {
    val dataBaseHelper = DataBaseHelper(context)
    var db:SQLiteDatabase? = null

    fun openDataBase(){
        db= dataBaseHelper.writableDatabase
    }
    fun insertDataBase(number:String, name:String, address: String, work: String, uri: String, time: String ){
        val values = ContentValues().apply {
            put(DataBaseClass.COLUMN_NAME_NUMBER, number)
            put(DataBaseClass.COLUMN_NAME_NAME_PEOPLE, name)
            put(DataBaseClass.COLUMN_NAME_ADDRESS, address)
            put(DataBaseClass.COLUMN_NAME_WORK, work)
            put(DataBaseClass.COLUMN_NAME_IMAGE_URI, uri)
            put(DataBaseClass.COLUMN_NAME_TIME, time)
        }
        db?.insert(DataBaseClass.TABLE_NAME, null, values)
    }

    fun updateDataBase (number:String, name:String, address: String, work: String, uri: String, id: Int, time: String ){
        val selection = BaseColumns._ID + "=$id"
        val values = ContentValues().apply {
            put(DataBaseClass.COLUMN_NAME_NUMBER, number)
            put(DataBaseClass.COLUMN_NAME_NAME_PEOPLE, name)
            put(DataBaseClass.COLUMN_NAME_ADDRESS, address)
            put(DataBaseClass.COLUMN_NAME_WORK, work)
            put(DataBaseClass.COLUMN_NAME_IMAGE_URI, uri)
            put(DataBaseClass.COLUMN_NAME_TIME, time)
        }
        db?.update(DataBaseClass.TABLE_NAME, values, selection, null)
    }

    fun deleteDataBase(id: String ){
        val selection = BaseColumns._ID + "=$id"

        db?.delete(DataBaseClass.TABLE_NAME, selection, null)
    }

    fun readDataBase(searchText:String): ArrayList<ListItem> {
        val dataList = ArrayList<ListItem>()
        val selection = "${DataBaseClass.COLUMN_NAME_NUMBER} like?"
        val orderBy1 = "${DataBaseClass.COLUMN_NAME_NUMBER} ASC"
        val cursor = db?.query(DataBaseClass.TABLE_NAME,null, selection, arrayOf("%$searchText%"),null,null,orderBy1)

        while (cursor?.moveToNext()!!){
            val dataNumber = cursor.getString(cursor.getColumnIndex(DataBaseClass.COLUMN_NAME_NUMBER))
            val dataName = cursor.getString(cursor.getColumnIndex(DataBaseClass.COLUMN_NAME_NAME_PEOPLE))
            val dataAddress = cursor.getString(cursor.getColumnIndex(DataBaseClass.COLUMN_NAME_ADDRESS))
            val dataWork = cursor.getString(cursor.getColumnIndex(DataBaseClass.COLUMN_NAME_WORK))
            val dataUri = cursor.getString(cursor.getColumnIndex(DataBaseClass.COLUMN_NAME_IMAGE_URI))
            val dataId = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID))
            val datatime = cursor.getString(cursor.getColumnIndex(DataBaseClass.COLUMN_NAME_TIME))

            val item = ListItem()
            item.number = dataNumber
            item.name = dataName
            item.address = dataAddress
            item.work = dataWork
            item.uri = dataUri
            item.id = dataId
            item.time = datatime

            dataList.add(item)
        }
        cursor.close()
        return dataList
    }

    fun closeDataBase(){
        dataBaseHelper.close()
    }



}