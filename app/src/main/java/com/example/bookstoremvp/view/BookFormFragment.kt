package com.example.bookstoremvp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.bookstoremvp.MemoryRepository
import com.example.bookstoremvp.R
import com.example.bookstoremvp.contracts.BookFormView
import com.example.bookstoremvp.model.Book
import com.example.bookstoremvp.presenter.BookFormPresenter
import kotlinx.android.synthetic.main.fragment_book_form.*

class BookFormFragment : DialogFragment(), BookFormView {
    private val presenter = BookFormPresenter(this, MemoryRepository)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_book_form, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        edtDescription.setOnEditorActionListener { _, actionId, _ ->
            handleKeyboardEvent(actionId)
        }

        dialog.setTitle(R.string.action_new_book)
        // Abre o teclado virtual ao exibir o Dialog
        dialog?.window?.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
    }

    private fun handleKeyboardEvent(actionId: Int): Boolean {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            val book = saveBook()
            if (book != null) {
                if (activity is OnBookSavedListener) {
                    val listener = activity as OnBookSavedListener
                    listener.onBookSaved(book)
                }
            }
            dialog.dismiss()
            return true
        }
        return false
    }

    private fun saveBook(): Book? {
        val book = Book()
        book.apply {
            name = edtName.text.toString()
            description = edtDescription.text.toString()
            rating = rtbRating.rating
        }
        return if (presenter.saveBook(book)) {
            book
        } else {
            null
        }
    }

    override fun errorInvalidBook() {
        Toast.makeText(
            requireContext(), "Livro Invalido",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun errorSaveBook() {
        Toast.makeText(
            requireContext(), "Error ao Salvar Livro",
            Toast.LENGTH_SHORT
        ).show()
    }

    interface OnBookSavedListener {
        fun onBookSaved(book: Book)
    }
}