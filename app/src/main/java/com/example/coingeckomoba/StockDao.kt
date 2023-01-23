package com.example.coingeckomoba

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface StockDao {
    @Query("SELECT * FROM stock")
    fun getAll(): List<Stock>

    @Query("SELECT * FROM stock WHERE stockId LIKE :stockId")
    fun loadStockById(stockId: String):List<Stock>

    @Insert
    fun insertAll(vararg stocks: Stock)
}