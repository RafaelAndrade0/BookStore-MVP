package com.example.bookstoremvp.presenter

import com.example.bookstoremvp.contracts.BookFormView
import com.example.bookstoremvp.contracts.BookRepository
import com.example.bookstoremvp.model.Book
import com.example.bookstoremvp.validators.BookValidator

class BookFormPresenter(
    private val view: BookFormView,
    private val repository: BookRepository
) {

    private val validator = BookValidator()

    fun saveBook(book: Book): Boolean {
        return if (validator.validate(book)) {
            try {
                repository.save(book)
                true
            } catch (e: Exception) {
                view.errorSaveBook()
                false
            }
        } else {
            view.errorInvalidBook()
            false
        }
    }
}