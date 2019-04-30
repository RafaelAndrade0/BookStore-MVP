package com.example.bookstoremvp.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import com.example.bookstoremvp.R
import com.example.bookstoremvp.model.Book
import com.example.bookstoremvp.view.BookFormFragment
import com.example.bookstoremvp.view.BookListFragment
import kotlinx.android.synthetic.main.activity_book_list.*

class BookListActivity : AppCompatActivity(),
    BookListFragment.OnBookClickListener,
    SearchView.OnQueryTextListener,
    MenuItem.OnActionExpandListener,
    BookFormFragment.OnBookSavedListener{

    private var lastSearchTerm = ""
    private var searchView: SearchView? = null

    private val listFragment: BookListFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.fragmentList) as BookListFragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_list)

        fabAdd.setOnClickListener {
            BookFormFragment().show(supportFragmentManager, "addLivro")
//            Toast.makeText(this, "Click Add", Toast.LENGTH_LONG).show()
        }
    }

    override fun onBookClick(book: Book) {
        showDetailsActivity(book.id)
    }

    private fun showDetailsActivity(bookId: Long) {
        BookDetailsActivity.open(this, bookId)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_book, menu)
        val searchItem = menu?.findItem(R.id.action_search)
        // Listener referente aos metodos de expand/colpase
        searchItem?.setOnActionExpandListener(this)
        searchView = searchItem?.actionView as SearchView
        searchView?.queryHint = "Procurar Livros"
        // Listener referente aos metodos de textChange/textSubmit
        searchView?.setOnQueryTextListener(this)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_info ->
                Toast.makeText(this, "Info", Toast.LENGTH_SHORT).show()
            R.id.action_search ->
                Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onQueryTextSubmit(query: String?): Boolean = true

    override fun onQueryTextChange(newText: String?): Boolean {
        lastSearchTerm = newText ?: ""
        listFragment.search(lastSearchTerm)
        return true
    }

    override fun onMenuItemActionExpand(item: MenuItem?): Boolean = true

    override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
        lastSearchTerm = ""
        listFragment.clearSearch()
        return true
    }

    override fun onBookSaved(book: Book) {
        listFragment.clearSearch()
    }
}
