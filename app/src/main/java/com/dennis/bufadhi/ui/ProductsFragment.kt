package com.dennis.bufadhi.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.dennis.bufadhi.BufadhiApplication
import com.dennis.bufadhi.R
import com.dennis.bufadhi.adapter.ProductAdapter
import com.dennis.bufadhi.database.Stock
import com.dennis.bufadhi.databinding.FragmentProductsBinding
import com.dennis.bufadhi.model.SalesViewModel
import com.dennis.bufadhi.model.SalesViewModelFactory

class ProductsFragment : Fragment() {

    private var binding: FragmentProductsBinding? = null
    private val salesViewModel: SalesViewModel by activityViewModels {
        SalesViewModelFactory(
            (activity?.application as BufadhiApplication).database
                .salesDao()
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val productsBinding = FragmentProductsBinding.inflate(inflater, container, false)
        binding = productsBinding
        return productsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val productAdapter = ProductAdapter { stock ->
            adapterOnClick(stock)
        }

        val recycler = binding?.recyclerView
        recycler?.adapter = productAdapter

        salesViewModel.allItemsAvailable.observe(this.viewLifecycleOwner) { stocks ->
            stocks.let {
                productAdapter.submitList(it as MutableList<Stock>)
            }
        }
        binding?.apply {
            floatingActionButton.setOnClickListener { addProduct() }
            fabHistory.setOnClickListener { findNavController().navigate(R.id.action_productsFragment_to_salesFragment)}
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun adapterOnClick(product: Stock) {
        val id = product.id
        val action = ProductsFragmentDirections.actionProductsFragmentToDetailsFragment(id)
        findNavController().navigate(action)
    }

    private fun addProduct() {
        val productName = binding?.itemName?.text.toString()
        val productQuantity = binding?.itemQuantity?.text.toString().toIntOrNull() ?: 0
        if (isEntryValid()) {
            salesViewModel.addNewStock(productName, productQuantity)
            binding?.apply {
                itemName.text?.clear()
                itemQuantity.text?.clear()
            }
        }
    }

    private fun isEntryValid(): Boolean {
        val name = binding?.itemName?.text.toString()
        val quantity = binding?.itemQuantity?.text.toString()
        return salesViewModel.entryValid(
            name, quantity
        )
    }
}