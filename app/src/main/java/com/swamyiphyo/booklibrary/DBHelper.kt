package com.swamyiphyo.booklibrary

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object{
        private const val DATABASE_NAME = "BookLibrary.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "books"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_AUTHOR = "author"
        private const val COLUMN_PAGES = "pages"
        private lateinit var db : SQLiteDatabase;
    }

    override fun onCreate(sqlitedatabase: SQLiteDatabase?) {
        val query : String = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_TITLE TEXT, $COLUMN_AUTHOR TEXT, $COLUMN_PAGES INTEGER);"
        sqlitedatabase?.execSQL(query)
    }

    override fun onUpgrade(sqlitedatabase: SQLiteDatabase?, p1: Int, p2: Int) {
        sqlitedatabase?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(sqlitedatabase)
    }
    fun addBook(title : String, author : String, pages : Int) : Long {
        db = writableDatabase

        /**
         * Basically, in the contentValues we store data from application and
         * pass it to the database.
         */
        val values = ContentValues()
        values.put(COLUMN_TITLE, title)
        values.put(COLUMN_AUTHOR, author)
        values.put(COLUMN_PAGES, pages)

        return db.insert(TABLE_NAME, null, values)
    }
    fun getAllBooks() : Cursor{
        db = readableDatabase
        //sql query for retrieving data
        val query = "SELECT * FROM $TABLE_NAME;"

        var cursor : Cursor? = null
        //if db is not null, then execute the query
        if(db != null){
            cursor = db.rawQuery(query, null)
        }
        return cursor!!
    }
    fun updateBook(id : String, title : String, author : String, pages : Int) : Int{
        db = writableDatabase

        val content = ContentValues()
        content.apply {
            put(COLUMN_TITLE, title)
            put(COLUMN_AUTHOR, author)
            put(COLUMN_PAGES, pages)
        }

        return db.update(TABLE_NAME, content, "$COLUMN_ID=?", arrayOf<String>(id))
    }
    fun deleteBook(id : String){
        db = writableDatabase
        db.delete(TABLE_NAME, "$COLUMN_ID=?", arrayOf(id))
    }
    fun deleteAllBooks(){
        db = writableDatabase
        db.execSQL("DELETE FROM $TABLE_NAME;")
    }
}