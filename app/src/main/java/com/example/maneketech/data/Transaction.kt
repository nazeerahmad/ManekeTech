package com.example.maneketech.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transaction_tab")
data class Transaction(
    @ColumnInfo(name = "amount")
    var amount:Int=0,
    @ColumnInfo(name = "note_100")
    var notes_100:Int=0,
    @ColumnInfo(name = "note_200")
    var notes_200:Int=0,
    @ColumnInfo(name = "note_500")
    var notes_500: Int=0,
    @ColumnInfo(name = "note_2000")
    var notes_2000:Int=0,
    @ColumnInfo(name = "timestamp")
    var time_stamp:String
){
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id:Int?=null
}
