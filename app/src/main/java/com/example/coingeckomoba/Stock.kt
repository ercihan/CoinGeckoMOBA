package com.example.coingeckomoba

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Stock (
    @PrimaryKey(autoGenerate = false) val stockId: String,
    @ColumnInfo(name="stockName") val stockName: String,
    @ColumnInfo(name="priceChf") val priceChf: Double,
    @ColumnInfo(name="image") val image: String
)