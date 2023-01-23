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
import kotlinx.coroutines.withContext
import java.net.URL


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateRecycleView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun updateRecycleView(){
        lifecycleScope.launchWhenStarted {
            withContext(Dispatchers.Default) {
                var stocks = DbCon.db?.stockDao()?.getAll()
                if(stocks != null){
                    var recyclerView: RecyclerView = binding.stocks
                    recyclerView.adapter = StockAdapter(stocks!!)
                    recyclerView.layoutManager = LinearLayoutManager(requireContext())
                }
            }
        }

    }
}