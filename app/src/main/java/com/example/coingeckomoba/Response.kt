package com.example.coingeckomoba

import com.google.gson.annotations.SerializedName

data class Response(
        val id: String,
        val name: String,
        @SerializedName("image")
        val imageThumb: Image,
        @SerializedName("market_data")
        val marketData: MarketData
    )