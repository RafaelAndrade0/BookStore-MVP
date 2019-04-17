package com.example.bookstoremvp.view.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bookstoremvp.R
import com.example.bookstoremvp.view.BookDetailsFragment

class BookDetailsActivity : AppCompatActivity() {

    private val bookId by lazy { intent.getLongExtra(EXTRA_BOOK_ID, -1) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_details)

        if (savedInstanceState == null) {
            showDetailsFragment()
        }
    }

    private fun showDetailsFragment() {
        val fragment = BookDetailsFragment.newInstance(bookId)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.details, fragment, BookDetailsFragment.TAG_DETAILS)
            .commit()
    }

    companion object {
        private const val EXTRA_BOOK_ID = "book_id"
        fun open(context: Context, bookId: Long) {
            context.startActivity(Intent(context, BookDetailsActivity::class.java).apply {
                putExtra(EXTRA_BOOK_ID, bookId)
            })
        }
    }
}
