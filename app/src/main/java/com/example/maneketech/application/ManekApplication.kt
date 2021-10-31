package com.example.maneketech.application

import android.app.Application
import com.example.maneketech.repo.TransactionRepo
import com.example.maneketech.room.AppDatabase

class ManekApplication :Application()  {

    val database by lazy { AppDatabase.getDatabaseClient(this) }
    val  repo by lazy { TransactionRepo(database.transactionDao()) }



    companion object {
        private var instance: ManekApplication? = null
        fun getInstance() : ManekApplication {
            return instance as ManekApplication
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

}