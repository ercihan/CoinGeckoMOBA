package com.example.coingeckomoba

import com.google.gson.annotations.SerializedName

class MarketData (
    @SerializedName("current_price")
    val currentPrice: CurrentPrice
)