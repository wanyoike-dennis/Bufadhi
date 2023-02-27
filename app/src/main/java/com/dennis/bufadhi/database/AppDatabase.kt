package com.dennis.bufadhi.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Sales::class,Stock::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun salesDao(): SalesDao

 companion object{
     @Volatile
     private var INSTANCE : AppDatabase? = null
     fun getDatabase(context:Context): AppDatabase{
         return INSTANCE ?: synchronized(this){
             val instance = Room.databaseBuilder(
                 context.applicationContext,
                 AppDatabase::class.java,
                 "sales"
             ).fallbackToDestructiveMigration()
                 .build()
             INSTANCE= instance
             return instance
         }
     }
 }
}