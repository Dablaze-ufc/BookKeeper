package com.dablaze.bookkeeper.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Book::class], version = 1)
abstract class BookDataBase : RoomDatabase() {

    abstract fun bookDAO(): BookDAO

    companion object{
        private var bookInstance : BookDataBase? = null

        fun getDataBase(context: Context) : BookDataBase?{
            if (bookInstance == null){
                synchronized(BookDataBase::class.java){
                    if (bookInstance == null){
                        bookInstance = Room.databaseBuilder<BookDataBase>(context.applicationContext,
                        BookDataBase::class.java,"book_database").build()
                    }
                }
            }
            return bookInstance
        }
    }
}