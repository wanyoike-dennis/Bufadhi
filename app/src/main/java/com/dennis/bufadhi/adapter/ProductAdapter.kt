package com.dennis.bufadhi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dennis.bufadhi.R
import com.dennis.bufadhi.database.Stock

class ProductAdapter(private val onClick: (Stock) -> Unit) :
  ListAdapter<Stock, ProductAdapter.ProductViewHolder>(ProductDiffCallback)
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.category_list_item,parent,false)
        return ProductViewHolder(view,onClick)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val stock = getItem(position)
        holder.bind(stock)
    }


    object ProductDiffCallback: DiffUtil.ItemCallback<Stock>(){
        override fun areItemsTheSame(oldItem: Stock, newItem: Stock): Boolean {
            return oldItem == newItem

        }

        override fun areContentsTheSame(oldItem: Stock, newItem: Stock): Boolean {
           return oldItem.id == newItem.id
        }

    }

    class  ProductViewHolder(itemView: View, val onClick: (Stock) -> Unit):
        RecyclerView.ViewHolder(itemView){
        private val productName : TextView = itemView.findViewById(R.id.txt_product_name)
        private val quantity: TextView=itemView.findViewById(R.id.txt_quantity)
        private var currentProduct: Stock? = null

        init {
            itemView.setOnClickListener {
                currentProduct?.let {
                    onClick(it)
                }
            }
        }

        fun bind(stock:Stock){
            currentProduct=stock
            productName.text = stock.name
            quantity.text=stock.quantity.toString()
        }

    }




}