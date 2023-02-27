package com.dennis.bufadhi.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dennis.bufadhi.database.Sales
import com.dennis.bufadhi.databinding.FragmentSalesBinding
import com.dennis.bufadhi.databinding.SummaryItemBinding

class SalesAdapter(private val onItemClicked: (Sales) -> Unit) :
ListAdapter<Sales, SalesAdapter.SalesViewHolder>(DiffCallback){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SalesViewHolder {
        return SalesViewHolder(
            SummaryItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: SalesViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }
        holder.bind(current)
    }

    class SalesViewHolder(private var binding: SummaryItemBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(sale:Sales){
            binding.apply {
                txtSumPName.text = sale.name
                txtSumQuantity.text= sale.quantity.toString()
                txtSumAmount.text=sale.price.toString()
            }
        }
        }
    companion object{
        private val DiffCallback = object : DiffUtil.ItemCallback<Sales>(){
            override fun areContentsTheSame(oldItem: Sales, newItem: Sales): Boolean {
                return oldItem.name==newItem.name
            }

            override fun areItemsTheSame(oldItem: Sales, newItem: Sales): Boolean {
                return oldItem == newItem
            }
        }
    }

}
