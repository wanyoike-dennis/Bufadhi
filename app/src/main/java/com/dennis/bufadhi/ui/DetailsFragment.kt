package com.dennis.bufadhi.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dennis.bufadhi.BufadhiApplication
import com.dennis.bufadhi.R
import com.dennis.bufadhi.database.Stock
import com.dennis.bufadhi.databinding.FragmentDetailsBinding
import com.dennis.bufadhi.model.SalesViewModel
import com.dennis.bufadhi.model.SalesViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class DetailsFragment : Fragment() {

    private val salesViewModel: SalesViewModel by activityViewModels {
        SalesViewModelFactory((activity?.application as BufadhiApplication).database
                .salesDao()) }
   private lateinit var stock: Stock
   private var binding: FragmentDetailsBinding? = null
   private val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentDetailsBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var currentProductId: Int? = null
        currentProductId = args.productId

        salesViewModel.retrieveItem(currentProductId).observe(this.viewLifecycleOwner)
        { selectedItem ->
            stock = selectedItem
            bind(stock)
        }
        binding?.btnSell1?.setOnClickListener { makeTextInputVisible() }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun makeTextInputVisible() {
        binding?.apply {
            textInputLayout1.visibility = View.VISIBLE
            textInputLayout2.visibility = View.VISIBLE
            btnSell1.visibility = View.GONE
            btnSellProduct.visibility = View.VISIBLE
        }
    }

    private fun isEntryValid(): Boolean {
        val name = binding?.txtName?.text.toString()
        val quantity = binding?.txtNumberAvailable?.text.toString()
        val price = binding?.quantity?.text.toString()
        return salesViewModel.isEntryValid(
            name, quantity, price
        )
    }

    private fun addNewSale() {
        val tempPrice = binding?.price?.text.toString().toIntOrNull() ?: 0
        val name = binding?.txtName?.text.toString()
        val quantity = binding?.quantity?.text.toString().toIntOrNull()?:0
        val price = quantity * tempPrice
        if (isEntryValid()) {
            salesViewModel.addNewItem(name, quantity, price)
            Toast.makeText(requireContext(), "item sold successful !", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_detailsFragment_to_salesFragment)
        }
    }
    private fun bind(stock: Stock) {
        binding?.apply {
            txtName.text = stock.name
            txtNumberAvailable.text = stock.quantity.toString()
            btnSellProduct.isEnabled = salesViewModel.isStockAvailable(stock)
            btnDelete.setOnClickListener { showConfirmationDialog() }
            btnSellProduct.setOnClickListener {
                val no = quantity.text.toString().toInt()
                salesViewModel.sellItem(stock,no)
                addNewSale()
            }


        }
    }

    private fun deleteItem(){
        salesViewModel.deleteItem(stock)
        findNavController().navigateUp()
    }

    private fun showConfirmationDialog(){
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(android.R.string.dialog_alert_title))
            .setMessage(getString(R.string.delete_question))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.no)) { _, _ -> }
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                deleteItem()
            }
            .show()
    }
}
