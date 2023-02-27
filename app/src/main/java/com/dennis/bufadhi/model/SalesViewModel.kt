package com.dennis.bufadhi.model

import androidx.lifecycle.*
import com.dennis.bufadhi.database.Sales
import com.dennis.bufadhi.database.SalesDao
import com.dennis.bufadhi.database.Stock
import kotlinx.coroutines.launch

class SalesViewModel(private val salesDao:SalesDao) : ViewModel() {
 val allSales: LiveData<List<Sales>> = salesDao.getSales().asLiveData()
 val allItemsAvailable: LiveData<List<Stock>> = salesDao.getStock().asLiveData()

    private fun insertItem(sales: Sales){
        viewModelScope.launch {
            salesDao.insertAll(sales)
        }
    }

    private fun insertStock(stock:Stock){
        viewModelScope.launch {
            salesDao.insertStockRecord(stock)
        }
    }

    fun retrieveItem(id:Int): LiveData<Stock>{
        return  salesDao.getItem(id).asLiveData()

    }
    private fun updateStock(stock:Stock){
         viewModelScope.launch {
             salesDao.updateStock(stock)
         }
     }
    fun sellItem(stock: Stock,no:Int){
        if (stock.quantity > 0){
            val newStock = stock.copy(quantity = stock.quantity - no)
            updateStock(newStock)
        }
    }

    fun isStockAvailable(stock: Stock) : Boolean{
        return( stock.quantity > 0)
    }

    fun deleteItem(stock: Stock){
        viewModelScope.launch {
            salesDao.delete(stock)
        }
    }

    private fun getNewItemEntry(
        productName:String,productQuantity:Int,productPrice:Int) : Sales{
        return Sales(
            name = productName,
            quantity = productQuantity,
            price = productPrice
        )
    }

    private fun getNewStockEntry(
        name:String,quantity:Int
    ):Stock{
        return Stock(name=name, quantity = quantity)
    }

    fun addNewItem(itemName:String,itemQuantity:Int,itemPrice:Int){
        val newItem= getNewItemEntry(itemName,itemQuantity,itemPrice)
        insertItem(newItem)
    }

    fun  addNewStock(name:String,quantity:Int){
        val newStock = getNewStockEntry(name,quantity)
        insertStock(newStock)

    }

    fun isEntryValid(itemName:String,itemQuantity:String,itemPrice:String) : Boolean{
        if (itemName.isBlank() || itemQuantity.isBlank() || itemPrice.isBlank()){
            return false
        }
        return true
    }

    fun entryValid(name:String,quantity:String): Boolean{
        if (name.isBlank() || quantity.isBlank()){
            return false
        }
        return true
    }
}
class SalesViewModelFactory(private val salesDao: SalesDao) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SalesViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return SalesViewModel(salesDao) as T
        }
        throw IllegalArgumentException("unknown viewModel class")
    }
}