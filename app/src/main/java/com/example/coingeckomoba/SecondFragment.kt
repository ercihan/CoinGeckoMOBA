package com.example.coingeckomoba

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coingeckomoba.databinding.FragmentSecondBinding
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

        binding.addStock.setOnClickListener {
            var response = addingStock()
            if(response != null){
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

    private fun sendReq(stock: String): Response{
        val gson = Gson()
        var responseJSON: String = URL("https://api.coingecko.com/api/v3/coins/$stock").readText()
        var response: Response = gson.fromJson(responseJSON, Response::class.java)
        return response
    }

    suspend fun storeInDB(id: String, name: String, imageThumb: String, priceInChf: Double){
        if(DbCon.db?.stockDao()?.loadStockById(id) == null){
            DbCon.db?.stockDao()?.insertAll(
                Stock(stockId = id, stockName = name, image = imageThumb, priceChf = priceInChf)
            )
        }
        else{
            DbCon.db?.stockDao()?.update(
                Stock(stockId = id, stockName = name, image = imageThumb, priceChf = priceInChf)
            )
        }

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