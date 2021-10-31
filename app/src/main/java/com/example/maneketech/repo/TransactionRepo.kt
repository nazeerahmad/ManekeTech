package com.example.maneketech.repo

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.maneketech.data.Transaction
import com.example.maneketech.room.DAOTransaction


class TransactionRepo(private val dao:DAOTransaction) {
    val allTransaction:LiveData<List<Transaction>> = dao.getAllTransaction()

    val lastTransaction:LiveData<Transaction> = dao.getLastTransaction()

    @WorkerThread
    suspend fun insert(transaction: Transaction) :Long{
       return dao.insertTransaction(transaction)

    }

}