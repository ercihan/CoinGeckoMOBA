package com.example.coingeckomoba

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StockAdapter(private val list: List<Stock>) : RecyclerView.Adapter<StockAdapter.StockViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return StockViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
        val task: Stock = list[position]
        holder.bind(task)
    }

    override fun getItemCount(): Int = list.size

    class StockViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(
            inflater.inflate(
                R.layout.stock_list_item,
                parent, false
            )
        ) {
        private var name: TextView? = null
        private var image: ImageView? = null //ToDo: Image not displayed..
        private var price: TextView? = null

        init {
            name = itemView.findViewById(R.id.stockname)
            image = itemView.findViewById(R.id.image)
            price = itemView.findViewById(R.id.price)
        }

        fun bind(stock: Stock) {
            name?.text = stock.stockName
            image?.setImageURI(Uri.parse("https://assets.coingecko.com/coins/images/9351/small/12ships.png?1566485390"))//ToDo: stock.image -> hier fehlt der Doppelpunkt in der URI
            price?.text = stock.priceChf.toString()
        }
    }
}