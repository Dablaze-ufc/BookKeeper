package com.dablaze.bookkeeper.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Book::class], version = 2)
abstract class BookDataBase : RoomDatabase() {

    abstract fun bookDAO(): BookDAO

    companion object{
        private var bookInstance : BookDataBase? = null

        val MIGRATION_1_2: Migration = object : Migration(1,2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE books " +
                        " ADD COLUMN description TEXT DEFAULT 'Add Description' " + " NOT NULL ")
            }

        }


        fun getDataBase(context: Context) : BookDataBase?{
            if (bookInstance == null){
                synchronized(BookDataBase::class.java){
                    if (bookInstance == null){
                        bookInstance = Room.databaseBuilder(context.applicationContext,
                        BookDataBase::class.java,"book_database").addMigrations(MIGRATION_1_2).build()
                    }
                }
            }
            return bookInstance
        }
    }
}