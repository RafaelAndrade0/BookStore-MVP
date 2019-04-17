package com.example.bookstoremvp.contracts

import com.example.bookstoremvp.model.Book

interface BookListView {
    fun showBooks(books: List<Book>)
    fun showBookDetails(book: Book)
    fun showDeleteMode()
    fun hideDeleteMode()
    fun showSelectedBooks(books: List<Book>)
    fun updateSelectionCountText(count: Int)
    fun reverseDeleteSnackbar(count: Int)
}