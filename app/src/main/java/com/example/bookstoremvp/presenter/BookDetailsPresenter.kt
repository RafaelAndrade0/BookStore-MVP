package com.example.bookstoremvp.presenter

import com.example.bookstoremvp.contracts.BookDetailsView
import com.example.bookstoremvp.contracts.BookRepository

class BookDetailsPresenter(
    private val view: BookDetailsView,
    private val repository: BookRepository
) {
    fun loadBookDetails(id: Long) {
        repository.bookById(id) { book ->
            if (book != null) {
                view.showBookDetails(book)
            } else {
                view.errorBookNotFound()
            }
        }
    }
}