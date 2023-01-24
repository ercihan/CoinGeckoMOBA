package com.example.coingeckomoba

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coingeckomoba.databinding.FragmentFirstBinding
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private var stocks: MutableList<Stock> = mutableListOf() //leere Mutable list

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        initializeRecycleView()

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var recyclerView: RecyclerView = binding.stocks
        recyclerView.adapter = StockAdapter(stocks!!)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        binding.addStock.setOnClickListener {
            var response = addingStock()
            if(response != null){
                addStock(Stock(
                    response!!.id,
                    response!!.name,
                    response!!.marketData.currentPrice.chf,
                    response!!.imageThumb.small)
                )

                (recyclerView.adapter as RecyclerView.Adapter).notifyDataSetChanged()
                lifecycleScope.launchWhenStarted {
                    withContext(Dispatchers.Default) {
                        storeInDB(
                            response!!.id,
                            response!!.name,
                            response!!.imageThumb.small,
                            response!!.marketData.currentPrice.chf
                        )
                    }
                }
            }
            else{
                binding.message.setText("The coin does not exist.")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun initializeRecycleView(){
        lifecycleScope.launch{
            withContext(Dispatchers.Default) {
                var stocksLocal = DbCon.db?.stockDao()?.getAll()
                if(stocksLocal != null){
                    stocks = stocksLocal as MutableList<Stock>
                }
            }
        }

    }

    private fun addStock(stock: Stock){
        if(stocks.filter { s-> s.stockId == stock.stockId }.size == 0){
            stocks.add(stock)
        }
    }
    private fun sendReq(stock: String): Response?{
        val gson = Gson()
        var response : Response? = null
        try {
            var responseJSON: String = URL("https://api.coingecko.com/api/v3/coins/$stock").readText()
            response = gson.fromJson(responseJSON, Response::class.java)
            binding.message.setText("")
        }
        catch (e: Exception){
            binding.message.setText("This coin does not exist.")
        }
        return response
    }

    suspend fun storeInDB(id: String, name: String, imageThumb: String, priceInChf: Double){
        DbCon.db?.stockDao()?.insertOrUpdateAll(
            Stock(stockId = id, stockName = name, image = imageThumb, priceChf = priceInChf)
        )
    }

    private fun addingStock(): Response?{
        var response : Response? = null
        var stockIdForCall : String = binding.stockId.text.toString()
        if(!stockIdForCall.equals("")){
            return sendReq(stockIdForCall)
        }
        return response
    }
}