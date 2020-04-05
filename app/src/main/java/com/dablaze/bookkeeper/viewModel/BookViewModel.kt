package com.dablaze.bookkeeper.viewModel

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.dablaze.bookkeeper.database.Book
import com.dablaze.bookkeeper.database.BookDAO
import com.dablaze.bookkeeper.database.BookDataBase


class BookViewModel(application: Application) : AndroidViewModel(application) {
    val allBooks: LiveData<List<Book>>
    private var bookDao:BookDAO
    init {
        val bookDb = BookDataBase.getDataBase(application)
        bookDao = bookDb?.bookDAO()!!
        allBooks = bookDao.allBooks
    }

    fun insert(book: Book){
        InsertAsyncTask(
            bookDao
        ).execute(book)
    }

    fun update(book: Book){
        UpdateAsyncTask(bookDao).execute(book)
    }


    companion object {
        private class InsertAsyncTask(private val bookDAO: BookDAO) :
            AsyncTask<Book, Void, Void>() {
            override fun doInBackground(vararg book: Book?): Void? {
                bookDAO.insert(book[0]!!)
                return null
            }

        }

        private class UpdateAsyncTask(private val bookDao: BookDAO): AsyncTask<Book, Void, Void>() {
            override fun doInBackground(vararg books: Book?): Void? {
                bookDao.update(books[0]!!)
                return null
            }

        }



    }
}