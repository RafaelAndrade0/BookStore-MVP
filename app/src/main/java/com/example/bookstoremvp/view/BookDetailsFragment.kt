package com.example.bookstoremvp.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bookstoremvp.MemoryRepository

import com.example.bookstoremvp.R
import com.example.bookstoremvp.contracts.BookDetailsView
import com.example.bookstoremvp.model.Book
import com.example.bookstoremvp.presenter.BookDetailsPresenter
import kotlinx.android.synthetic.main.fragment_book_details.*

class BookDetailsFragment : Fragment(), BookDetailsView {

    private var book: Book? = null
    private var presenter = BookDetailsPresenter(this, MemoryRepository)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.loadBookDetails(arguments?.getLong(EXTRA_BOOK_ID, -1) ?: -1)
    }

    override fun showBookDetails(book: Book) {
        this.book = book
        textName.text = book.name
        textDescription.text = book.description
        ratingBar.rating = book.rating
    }

    override fun errorBookNotFound() {
        textName.text = getString(R.string.book_not_found)
        textDescription.visibility = View.GONE
        ratingBar.visibility = View.GONE
    }


    companion object {
        const val TAG_DETAILS = "tagDetalhe"
        private const val EXTRA_BOOK_ID = "hotelId"

        fun newInstance(id: Long) =
            BookDetailsFragment().apply {
                arguments = Bundle().apply {
                    putLong(EXTRA_BOOK_ID, id)
                }
            }
    }
}
