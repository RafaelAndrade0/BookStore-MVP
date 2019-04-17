package com.example.bookstoremvp.contracts

import com.example.bookstoremvp.model.Book

interface BookDetailsView {
    fun showBookDetails(book: Book)
    fun errorBookNotFound()
}