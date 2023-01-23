package com.example.coingeckomoba

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface StockDao {
    @Query("SELECT * FROM stock")
    fun getAll(): List<Stock>

    @Query("SELECT * FROM stock WHERE stockId LIKE :stockId")
    fun loadStockById(stockId: String):List<Stock>

    @Insert
    fun insertAll(vararg stocks: Stock)

    @Update
    fun updateAll(vararg stock: Stock)
    fun insertOrUpdateAll(vararg coins:Stock) {
        val coinList:List<Stock> = getAll()
        coins.forEach {stock ->
            if(coinList.filter { c -> c.stockId.equals(stock.stockId) }.size>0) {
                updateAll(stock)
            }else{
                insertAll(stock)
            }
        }
    }
}