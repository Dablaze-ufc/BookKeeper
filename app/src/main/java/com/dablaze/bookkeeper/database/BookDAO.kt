package com.dablaze.bookkeeper.database

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface BookDAO {

    @Insert
    fun insert(book:Book)
}