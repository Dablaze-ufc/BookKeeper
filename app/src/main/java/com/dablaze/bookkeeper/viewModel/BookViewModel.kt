package com.dablaze.bookkeeper.viewModel

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.AndroidViewModel
import com.dablaze.bookkeeper.database.Book
import com.dablaze.bookkeeper.database.BookDAO
import com.dablaze.bookkeeper.database.BookDataBase


class BookViewModel(application: Application) : AndroidViewModel(application) {
    private var bookDao:BookDAO
    init {
        val bookDb = BookDataBase.getDataBase(application)
        bookDao = bookDb?.bookDAO()!!
    }

    fun insert(book: Book){
        InsertAsyncTask(
            bookDao
        ).execute(book)
    }

    companion object {
        private class InsertAsyncTask(private val bookDAO: BookDAO) :
            AsyncTask<Book, Void, Void>() {
            override fun doInBackground(vararg book: Book?): Void? {
                bookDAO.insert(book[0]!!)
                return null
            }

        }
    }
}