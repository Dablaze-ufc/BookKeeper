package com.dablaze.bookkeeper.viewModel

import android.app.Application

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.dablaze.bookkeeper.database.Book
import com.dablaze.bookkeeper.repository.BookRepository


class BookViewModel(application: Application) : AndroidViewModel(application) {
    val allBooks: LiveData<List<Book>>
    private val bookRepo = BookRepository(application)

    init {
        allBooks = bookRepo.allBooks
    }

    fun insert(book: Book) = bookRepo.insert(book)

    fun update(book: Book) = bookRepo.update(book)

    fun delete(book: Book) = bookRepo.delete(book)
}