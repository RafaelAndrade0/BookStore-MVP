package com.example.bookstoremvp.repository.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.bookstoremvp.contracts.BookRepository
import com.example.bookstoremvp.model.Book

class SQLiteRepository(ctx: Context) : BookRepository {

    private val helper: BookSqlHelper = BookSqlHelper(ctx)

    private fun insert(book: Book) {
        val db = helper.writableDatabase
        val cv = ContentValues().apply {
            put(COLUMN_NAME, book.name)
            put(COLUMN_DESCRIPTION, book.description)
            put(COLUMN_RATING, book.rating)
        }
        // db.insert retorna -1 caso tenha ocorrido algum problema
        val id = db.insert(TABLE_BOOK, null, cv)
        if (id != -1L) {
            book.id = id
        }
        db.close()
    }

    private fun update(book: Book) {
        val db = helper.writableDatabase
        val cv = ContentValues().apply {
            put(COLUMN_ID, book.id)
            put(COLUMN_NAME, book.name)
            put(COLUMN_DESCRIPTION, book.description)
            put(COLUMN_RATING, book.rating)
        }
        db.insertWithOnConflict(
            TABLE_BOOK,
            null,
            cv,
            SQLiteDatabase.CONFLICT_REPLACE
        )
    }

    override fun save(book: Book) {
        if (book.id == 0L) {
            insert(book)
        } else {
            update(book)
        }
    }

    override fun remove(vararg books: Book) {
        val db = helper.writableDatabase
        for (book in books) {
            db.delete(
                TABLE_BOOK,
                "$COLUMN_ID = ?",
                arrayOf(book.id.toString())
            )
        }
        db.close()
    }

    override fun bookById(id: Long, callback: (Book?) -> Unit) {
        val sql = "SELECT * FROM $TABLE_BOOK WHERE $COLUMN_ID = ?"
        val db = helper.readableDatabase
        val cursor = db.rawQuery(sql, arrayOf(id.toString()))
        val book = if (cursor.moveToNext()) bookFromCursor(cursor) else null
        callback(book)
    }

    override fun search(term: String, callback: (List<Book>) -> Unit) {
        var sql = "SELECT * FROM $TABLE_BOOK"
        var args: Array<String>? = null
        if (term.isNotEmpty()) {
            sql += " WHERE $COLUMN_NAME LIKE ?"
            args = arrayOf("%$term%")
        }
        sql += " ORDER BY $COLUMN_NAME"
        val db = helper.readableDatabase
        val cursor = db.rawQuery(sql, args)
        val books = ArrayList<Book>()
        while (cursor.moveToNext()) {
            val hotel = bookFromCursor(cursor)
            books.add(hotel)
        }
        cursor.close()
        db.close()
        callback(books)
    }

    private fun bookFromCursor(cursor: Cursor): Book {
        val id = cursor.getLong(
            cursor.getColumnIndex(COLUMN_ID))
        val name = cursor.getString(
            cursor.getColumnIndex(COLUMN_NAME))
        val description = cursor.getString(
            cursor.getColumnIndex(COLUMN_DESCRIPTION))
        val rating = cursor.getFloat(
            cursor.getColumnIndex(COLUMN_RATING))
        return Book(id, name, description, rating)
    }

}