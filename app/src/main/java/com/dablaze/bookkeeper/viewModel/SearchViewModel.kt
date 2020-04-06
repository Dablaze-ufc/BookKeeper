package com.dablaze.bookkeeper.viewModel

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.dablaze.bookkeeper.database.Book
import com.dablaze.bookkeeper.database.BookDAO
import com.dablaze.bookkeeper.database.BookDataBase

class SearchViewModel(application: Application): AndroidViewModel(application) {

    private var bookDao: BookDAO
    init {
        val bookDb = BookDataBase.getDataBase(application)
        bookDao = bookDb?.bookDAO()!!

    }
    fun getSearchedBooks(searchQuery:String):LiveData<List<Book>>{
        return bookDao.searchAllBooks(searchQuery)
    }
    fun update(book: Book){
        UpdateAsyncTask(bookDao).execute(book)
    }

    fun delete(book: Book){
        DeleteAsyncTask(bookDao).execute(book)
    }

    companion object {
        private class UpdateAsyncTask(private val bookDao: BookDAO) :
            AsyncTask<Book, Void, Void>() {
            override fun doInBackground(vararg books: Book?): Void? {
                bookDao.update(books[0]!!)
                return null
            }

        }

        private class DeleteAsyncTask(private val bookDao: BookDAO) :
            AsyncTask<Book, Void, Void>() {
            override fun doInBackground(vararg books: Book?): Void? {
                bookDao.delete(books[0]!!)
                return null
            }
        }
    }
}




