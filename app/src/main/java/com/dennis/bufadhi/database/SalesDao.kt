package com.dennis.bufadhi.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface SalesDao {

    @Query("SELECT * From stock ORDER BY product_name")
    fun getStock() : Flow<List<Stock>>

    @Query("SELECT * from stock WHERE id = :id")
    fun getItem(id: Int): Flow<Stock>

    @Query("SELECT * FROM sales ORDER BY id DESC")
    fun getSales(): Flow<List<Sales>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
   suspend fun insertAll(vararg sale:Sales)

   @Insert(onConflict = OnConflictStrategy.IGNORE)
   suspend fun insertStockRecord(vararg stock: Stock)

   @Update
   suspend fun updateStock(stock: Stock)

    @Delete
   suspend fun delete(stock:Stock)
}