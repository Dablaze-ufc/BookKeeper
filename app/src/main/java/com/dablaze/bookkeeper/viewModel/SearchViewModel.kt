package com.dablaze.bookkeeper.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.dablaze.bookkeeper.database.Book
import com.dablaze.bookkeeper.repository.BookRepository

class SearchViewModel(application: Application): AndroidViewModel(application) {

    private var bookRepo =  BookRepository(application)
    fun getSearchedBooks(searchQuery:String) = bookRepo.getSearchedBooks(searchQuery)
    fun update(book: Book) = bookRepo.update(book)

    fun delete(book: Book) = bookRepo.delete(book)
}




