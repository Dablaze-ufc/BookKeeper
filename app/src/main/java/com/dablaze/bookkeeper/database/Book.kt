package com.dablaze.bookkeeper.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "books")
class Book(@PrimaryKey
           val id : String,
           val author:String,
           val bookName: String,
           val description: String,
           @ColumnInfo(name = "last_updated")
           val lastUpdated: Date?)