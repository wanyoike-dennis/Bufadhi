package com.dennis.bufadhi.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sales")
data class Sales(
  @PrimaryKey(autoGenerate = true)  val id:Int=0,
   @ColumnInfo(name="product_name") val name:String,
   @ColumnInfo(name="product_quantity") val quantity:Int,
   @ColumnInfo(name="product_price") val price:Int
)
