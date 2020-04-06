package com.dablaze.bookkeeper.repository

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.dablaze.bookkeeper.database.Book
import com.dablaze.bookkeeper.database.BookDAO
import com.dablaze.bookkeeper.database.BookDataBase

class BookRepository(application: Application) {
    val allBooks: LiveData<List<Book>>
    private var bookDao: BookDAO
    init {
        val bookDb = BookDataBase.getDataBase(application)
        bookDao = bookDb?.bookDAO()!!
        allBooks = bookDao.allBooks
    }
    fun getSearchedBooks(searchQuery:String):LiveData<List<Book>>{
        return bookDao.searchAllBooks(searchQuery)
    }

    fun insert(book: Book){
        InsertAsyncTask(
            bookDao
        ).execute(book)
    }

    fun update(book: Book){
        UpdateAsyncTask(bookDao).execute(book)
    }
    fun delete(book: Book){
        DeleteAsyncTask(bookDao).execute(book)
    }


    companion object {

        private class DeleteAsyncTask(private val bookDao: BookDAO) :
            AsyncTask<Book, Void, Void>() {
            override fun doInBackground(vararg books: Book?): Void? {
                bookDao.delete(books[0]!!)
                return null
            }
        }
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