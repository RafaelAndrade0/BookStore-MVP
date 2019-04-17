package com.example.bookstoremvp.contracts

import com.example.bookstoremvp.model.Book

interface BookRepository {
    fun save(book: Book)
    fun remove(vararg books: Book)
    fun bookById(id: Long, callback: (Book?) -> Unit)
    fun search(term: String, callback: (List<Book>) -> Unit)
}