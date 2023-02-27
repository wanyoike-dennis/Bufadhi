package com.dennis.bufadhi

import android.app.Application
import com.dennis.bufadhi.database.AppDatabase

class BufadhiApplication : Application() {
    val database:AppDatabase by lazy { AppDatabase.getDatabase(this)}
}