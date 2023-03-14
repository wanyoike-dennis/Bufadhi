package com.dennis.bufadhi.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.dennis.bufadhi.BufadhiApplication
import com.dennis.bufadhi.adapter.SalesAdapter
import com.dennis.bufadhi.databinding.FragmentSalesBinding
import com.dennis.bufadhi.model.SalesViewModel
import com.dennis.bufadhi.model.SalesViewModelFactory

class SalesFragment : Fragment() {
   private var binding:FragmentSalesBinding? = null

    private val salesViewModel: SalesViewModel by activityViewModels {
        SalesViewModelFactory((activity?.application as BufadhiApplication) .database.salesDao()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentSalesBinding.inflate(inflater,container,false)
        binding=fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val adapter = SalesAdapter{}
        binding?.summaryRecyclerView?.adapter = adapter

        salesViewModel.allSales.observe(this.viewLifecycleOwner){
            sales -> sales.let { adapter.submitList(it) }
        }
      //  binding?.txtTotals?.text = salesViewModel.getSumPrice().toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding=null
    }
}