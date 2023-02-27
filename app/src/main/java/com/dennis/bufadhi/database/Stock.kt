package com.dennis.bufadhi.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stock")
data class Stock(
    @PrimaryKey(autoGenerate = true)  val id:Int=0,
    @ColumnInfo(name="product_name") val name:String,
    @ColumnInfo(name="product_quantity_available") val quantity:Int
)
