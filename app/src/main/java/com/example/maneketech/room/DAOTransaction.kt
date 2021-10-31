package com.example.maneketech.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.maneketech.data.Transaction

@Dao
interface DAOTransaction {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: Transaction):Long

    @Query("SELECT * FROM transaction_tab")
    fun getAllTransaction():LiveData<List<Transaction>>

    @Query("SELECT * FROM transaction_tab ORDER BY timestamp DESC  LIMIT 1")
    fun getLastTransaction():LiveData<Transaction>
}