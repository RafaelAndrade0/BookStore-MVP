package com.example.bookstoremvp.presenter

import android.widget.Toast
import com.example.bookstoremvp.contracts.BookListView
import com.example.bookstoremvp.contracts.BookRepository
import com.example.bookstoremvp.model.Book

class BookListPresenter(
    private val view: BookListView,
    private val repository: BookRepository
) {

    private var lastTerm = ""
    private var inDeleteMode = false
    private val selectedItems = mutableListOf<Book>()
    private val deletedItems = mutableListOf<Book>()

    fun searchBooks(term: String) {
        lastTerm = term
        repository.search(term) { books ->
            view.showBooks(books)
        }
    }

    fun showBookDetails(book: Book) {
        view.showBookDetails(book)
    }

    fun selectBook(book: Book) {
        if (inDeleteMode) {
            toogleBookSelected(book)
            // Não há itens selecionados, sai do modo se seleção
            if (selectedItems.size == 0) {
                view.hideDeleteMode()
            } else {
                view.updateSelectionCountText(selectedItems.size)
                view.showSelectedBooks(selectedItems)
            }
        } else {
            view.showBookDetails(book)
        }
    }

    private fun toogleBookSelected(book: Book) {
        val existing = selectedItems.find { it.id == book.id }
        if (existing == null) {
            selectedItems.add(book)
        } else {
            selectedItems.removeAll { it.id == book.id }
        }
    }

    fun showDeleteMode() {
        inDeleteMode = true
        view.showDeleteMode()
    }

    fun hideDeleteMode() {
        inDeleteMode = false
        selectedItems.clear()
        view.hideDeleteMode()
    }

    private fun refresh() {
        searchBooks(lastTerm)
    }

//    private fun reverseDeleteSnackbar(count: Int) {
//        view.reverseDeleteSnackbar(count)
//    }

    // Deleta a lista de elementos naquele momento
    fun deleteSelected(callback: (List<Book>?) -> Unit) {
        deletedItems.clear()
        deletedItems.addAll(selectedItems)
        repository.remove(*selectedItems.toTypedArray())
        refresh()
        callback(selectedItems)
        hideDeleteMode()
        view.reverseDeleteSnackbar(deletedItems.size)
    }

    fun undoDelete() {
        for (book in deletedItems) {
            repository.save(book)
        }
        refresh()
    }
}