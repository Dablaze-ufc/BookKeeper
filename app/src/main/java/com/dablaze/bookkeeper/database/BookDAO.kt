package com.dablaze.bookkeeper.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface BookDAO {

    @Insert
    fun insert(book:Book)

    @get: Query("SELECT * FROM books")
    val allBooks: LiveData<List<Book>>

    @Update
    fun update(book: Book)

}