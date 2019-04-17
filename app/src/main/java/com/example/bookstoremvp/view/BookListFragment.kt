package com.example.bookstoremvp.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.fragment.app.ListFragment
import com.example.bookstoremvp.MemoryRepository
import com.example.bookstoremvp.R
import com.example.bookstoremvp.contracts.BookListView
import com.example.bookstoremvp.model.Book
import com.example.bookstoremvp.presenter.BookListPresenter
import com.google.android.material.snackbar.Snackbar

class BookListFragment : ListFragment(),
    BookListView,
    AdapterView.OnItemLongClickListener,
    ActionMode.Callback {

    private val presenter = BookListPresenter(this, MemoryRepository)
    private var actionMode: ActionMode? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter.searchBooks("")
        listView.onItemLongClickListener = this
    }

    override fun showBooks(books: List<Book>) {
        val adapter = ArrayAdapter<Book>(requireContext(), android.R.layout.simple_list_item_activated_1, books)
        listAdapter = adapter
    }

    override fun showBookDetails(book: Book) {
        if (activity is OnBookClickListener) {
            val listener = activity as OnBookClickListener
            listener.onBookClick(book)
        }
    }

    override fun onListItemClick(l: ListView?, v: View?, position: Int, id: Long) {
        super.onListItemClick(l, v, position, id)
        val book = l?.getItemAtPosition(position) as Book
//        presenter.showBookDetails(book)
        presenter.selectBook(book)
    }

    fun search(term: String) {
        presenter.searchBooks(term)
    }

    fun clearSearch() {
        presenter.searchBooks("")
    }

    override fun onItemLongClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long): Boolean {
        val consumed = (actionMode == null)
        if (consumed) {
            val book = parent?.getItemAtPosition(position) as Book
            presenter.showDeleteMode()
            presenter.selectBook(book)
        }
        return true
    }

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_delete) {
            Toast.makeText(requireContext(), "Deletar", Toast.LENGTH_SHORT).show()
            presenter.deleteSelected { book ->
//                Toast.makeText(requireContext(), "Livros Removidos", Toast.LENGTH_SHORT).show()
            }
        }
        return true
    }

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        activity?.menuInflater?.inflate(R.menu.book_menu_list, menu)
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean = false

    override fun onDestroyActionMode(mode: ActionMode?) {
        actionMode = null
        presenter.hideDeleteMode()
    }

    override fun showDeleteMode() {
        val appCompatActivity = (activity as AppCompatActivity)
        actionMode = appCompatActivity.startSupportActionMode(this)
        listView.onItemLongClickListener = null
        listView.choiceMode = ListView.CHOICE_MODE_MULTIPLE
    }

    override fun hideDeleteMode() {
        listView.onItemLongClickListener = this
        for (i in 0 until listView.count) {
            listView.setItemChecked(i, false)
        }
        listView.post {
            actionMode?.finish()
            listView.choiceMode = ListView.CHOICE_MODE_NONE
        }
    }

    override fun showSelectedBooks(books: List<Book>) {
        listView.post {
            for (i in 0 until listView.count) {
                val book = listView.getItemAtPosition(i) as Book
                if (books.find { it.id == book.id } != null) {
                    listView.setItemChecked(i, true)
                }
            }
        }
    }

    override fun updateSelectionCountText(count: Int) {
//        view?.post {
//            actionMode?.title = "1 Selecionado"
//        }
        view?.post {
            actionMode?.title = resources.getQuantityString(
                R.plurals.list_hotel_selected,
                count, count
            )
        }
    }

    override fun reverseDeleteSnackbar(count: Int) {
        Snackbar.make(listView, getString(R.string.message_hotels_deleted, count), Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.undo) {
                presenter.undoDelete()
            }
            .show()
    }

    interface OnBookClickListener {
        fun onBookClick(book: Book)
    }
}