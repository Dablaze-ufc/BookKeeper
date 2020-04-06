package com.dablaze.bookkeeper.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.dablaze.bookkeeper.util.DateTypeConverter

@Database(entities = [Book::class], version = 3)
@TypeConverters(DateTypeConverter::class)
abstract class BookDataBase : RoomDatabase() {

    abstract fun bookDAO(): BookDAO

    companion object{
        private var bookInstance : BookDataBase? = null

        private val MIGRATION_1_2: Migration = object : Migration(1,2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE books " +
                        " ADD COLUMN description TEXT DEFAULT 'Add Description' " + " NOT NULL ")
            }

        }

        private val MIGRATION_2_3: Migration = object : Migration(2,3){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE books " + " ADD COLUMN last_updated INTEGER DEFAULT NULL ")
            }

        }


        fun getDataBase(context: Context) : BookDataBase?{
            if (bookInstance == null){
                synchronized(BookDataBase::class.java){
                    if (bookInstance == null){
                        bookInstance = Room.databaseBuilder(context.applicationContext,
                        BookDataBase::class.java,"book_database").addMigrations(MIGRATION_1_2, MIGRATION_2_3).build()
                    }
                }
            }
            return bookInstance
        }
    }
}