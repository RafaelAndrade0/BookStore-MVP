package com.example.bookstoremvp.repository.sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BookSqlHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE $TABLE_BOOK (" +
                    "$COLUMN_ID  INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "$COLUMN_NAME  TEXT NOT NULL, " +
                    "$COLUMN_DESCRIPTION  TEXT, " +
                    "$COLUMN_RATING REAL)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Para próximas versões")
    }
}