package com.example.bookstoremvp.model

data class Book(
    var id: Long = 0,
    var name: String = "",
    var description: String = "",
    var rating:Float = 0.0F
) {
    override fun toString(): String = name
}