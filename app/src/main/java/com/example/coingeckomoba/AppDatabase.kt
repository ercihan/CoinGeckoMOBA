package com.example.coingeckomoba

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Stock::class), version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun stockDao(): StockDao
}