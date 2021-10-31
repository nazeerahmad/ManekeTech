package com.example.maneketech.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.maneketech.application.ManekApplication
import com.example.maneketech.data.Transaction
import com.example.maneketech.repo.TransactionRepo
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class TransactionViewModel():ViewModel() {

    private val repo = ManekApplication.getInstance().repo
    val transactions:LiveData<List<Transaction>> =   repo.allTransaction
    val lastTransaction:LiveData<Transaction> = repo.lastTransaction


    fun insert(transaction: Transaction)= viewModelScope.launch {
        repo.insert(transaction)
    }
}
//class TransactionViewModelFactory(private val repo:TransactionRepo): ViewModelProvider.Factory{
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if(modelClass.isAssignableFrom(TransactionViewModel::class.java)){
//            @Suppress("UNCHECKED_CAST")
//            return TransactionViewModel(repo) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//
//}