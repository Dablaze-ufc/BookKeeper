package com.dablaze.bookkeeper.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BookDAO {

    @Insert
    fun insert(book:Book)

    @get: Query("SELECT * FROM books")
    val allBooks: LiveData<List<Book>>

    @Update
    fun update(book: Book)

    @Query("SELECT * FROM books WHERE author LIKE :searchString OR bookName LIKE :searchString")
    fun searchAllBooks(searchString: String): LiveData<List<Book>>

    @Delete
    fun delete(book:Book)

}