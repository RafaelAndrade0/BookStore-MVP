package com.example.bookstoremvp.di

import com.example.bookstoremvp.contracts.BookDetailsView
import com.example.bookstoremvp.contracts.BookFormView
import com.example.bookstoremvp.contracts.BookListView
import com.example.bookstoremvp.contracts.BookRepository
import com.example.bookstoremvp.presenter.BookDetailsPresenter
import com.example.bookstoremvp.presenter.BookFormPresenter
import com.example.bookstoremvp.presenter.BookListPresenter
import com.example.bookstoremvp.repository.sqlite.SQLiteRepository
import org.koin.dsl.module.module

val androidModule = module {
    single { this }
    single {
        SQLiteRepository(ctx = get()) as BookRepository
    }
    factory { (view: BookListView) ->
        BookListPresenter(
            view,
            repository = get()
        )
    }
    factory { (view: BookDetailsView) ->
        BookDetailsPresenter(
            view,
            repository = get()
        )
    }
    factory { (view: BookFormView) ->
        BookFormPresenter(
            view,
            repository = get()
        )
    }
}