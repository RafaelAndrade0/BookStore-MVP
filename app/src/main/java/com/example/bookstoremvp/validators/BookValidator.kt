package com.example.bookstoremvp.validators

import com.example.bookstoremvp.model.Book

class BookValidator {
//    fun validate(info: Book): Boolean {
//        return checkName(info.name) && checkDescription(info.description)
//    }

    fun validate(info: Book) = with(info) {
        checkName(name) && checkDescription(description)
    }

    private fun checkName(name: String) = name.length in 2..20
    private fun checkDescription(description: String) = description.length in 4..80
}