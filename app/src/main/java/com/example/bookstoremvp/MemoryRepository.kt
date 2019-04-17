package com.example.bookstoremvp

import com.example.bookstoremvp.contracts.BookRepository
import com.example.bookstoremvp.model.Book

object MemoryRepository : BookRepository {

    private var nextId: Long = 1L
    private var bookList = mutableListOf<Book>()

    init {
        save(Book(0, "The Philosopher's Stone", "The First Book", 4.5f))
        save(Book(0, "The Chamber of Secrets", "The Second Book", 1.5f))
        save(Book(0, "The Prisoner of Azkaban", "The Third Book", 2.5f))
        save(Book(0, "The Goblet of Fire", "The Fourth Book", 0.5f))
        save(Book(0, "The Order of the Phoenix", "The Fifth Book", 5.0f))
        save(Book(0, "The Half-Blood Prince", "The Sixth Book", 3.5f))
        save(Book(0, "The Deathly Hallows", "The seventh Book", 1.0f))
    }

    override fun save(book: Book) {
        if (book.id == 0L) {
            book.id = nextId++
            bookList.add(book)
        } else {
            val index = bookList.indexOfFirst { it.id == book.id }
            if (index > -1) {
                bookList[index] = book
            } else {
                bookList.add(book)
            }
        }
    }

    override fun remove(vararg books: Book) {
        bookList.removeAll(books)
    }

    override fun bookById(id: Long, callback: (Book?) -> Unit) {
        callback(bookList.find { it.id == id })
    }

    override fun search(term: String, callback: (List<Book>) -> Unit) {
        val resultList = if (term.isEmpty()) bookList
        else bookList.filter {
            it.name.toUpperCase().contains(term.toUpperCase())
        }
        callback(resultList.sortedBy { it.name })
    }
}