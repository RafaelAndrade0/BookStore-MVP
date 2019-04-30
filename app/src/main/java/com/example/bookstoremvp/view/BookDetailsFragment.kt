package com.example.bookstoremvp.view


import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.ShareActionProvider
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.Fragment
import com.example.bookstoremvp.R
import com.example.bookstoremvp.contracts.BookDetailsView
import com.example.bookstoremvp.model.Book
import com.example.bookstoremvp.presenter.BookDetailsPresenter
import kotlinx.android.synthetic.main.fragment_book_details.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class BookDetailsFragment : Fragment(), BookDetailsView {

    private var book: Book? = null
//    private var presenter = BookDetailsPresenter(this, MemoryRepository)
    private val presenter: BookDetailsPresenter by inject { parametersOf(this) }
    private var shareActionProvider: ShareActionProvider? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

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

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.book_details, menu)
        val shareItem = menu?.findItem(R.id.action_share)
        shareActionProvider = MenuItemCompat.getActionProvider(shareItem) as? ShareActionProvider
        setShareIntent()
    }

    private fun setShareIntent() {
        val text = getString(R.string.share_text, book?.name, book?.rating)
        shareActionProvider?.setShareIntent(Intent(Intent.ACTION_SEND).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT)
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, text)
        })
    }
//
//    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
//        when (item?.itemId) {
//            R.id.action_share ->
//                Toast.makeText(requireContext(), "Share", Toast.LENGTH_LONG).show()
//            R.id.action_edit -> {
//                BookFormFragment
//            }
//        }
//        return super.onOptionsItemSelected(item)
//    }


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
