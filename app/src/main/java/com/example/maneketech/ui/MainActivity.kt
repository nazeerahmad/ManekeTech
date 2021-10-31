package com.example.maneketech.ui


import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.lifecycle.Observer
import com.example.maneketech.R
import com.example.maneketech.base.BaseActivity
import com.example.maneketech.data.Transaction
import com.example.maneketech.databinding.ActivityMainBinding
import com.example.maneketech.viewmodel.TransactionViewModel
import com.google.android.material.snackbar.Snackbar
import java.util.concurrent.TimeUnit

class MainActivity : BaseActivity<ActivityMainBinding,TransactionViewModel>() {

    private val tHeaders = arrayOf("ATM Amount", "Rs.100", "Rs.200", "Rs.500", "Rs.2000")
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }
    private var avaliable = 50000
    private var tot_100=75
    private var tot_200=50
    private var tot_500=25
    private var tot_2000=10
    private  var withdrawal:Int = 0
    override fun getViewModel(): Class<TransactionViewModel> {
//       val txnViewModel: TransactionViewModel by viewModels{
//           TransactionViewModelFactory((application as ManekApplication).repo)
//       }

        return TransactionViewModel::class.java

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding.viewModel=viewModel
        initView()

    }
    private fun initView(){
        dataBinding.tvTotAmount.text= "$avaliable"
        dataBinding.tvNotes100.text="$tot_100"
        dataBinding.tvNotes200.text="$tot_200"
        dataBinding.tvNotes500.text="$tot_500"
        dataBinding.tvNotes2000.text="$tot_2000"
        getTxnList()
        lastTxn()
        dataBinding.tvWithdrawBtn.setOnClickListener {
            val amount= dataBinding.edAmount.text.toString()
            if(amount.isEmpty()){
                Snackbar.make(dataBinding.root,"Please Enter Amount",Snackbar.LENGTH_SHORT).show()
            }else{
                calculate(amount.toInt())
            }
        }
//        initTxn()
    }



    private fun initTxnViews(transactions:ArrayList<Transaction>){
        dataBinding.tableMain.removeAllViews()
        val layoutParams = TableLayout.LayoutParams(
            TableLayout.LayoutParams.MATCH_PARENT,
            TableLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(0, 0, 2, 0)
        val tableRowHeader = TableRow(this)
        dataBinding.tableMain.addView(tableRowHeader, layoutParams)
        tableRowHeader.setBackgroundResource(R.color.white)
        tHeaders?.forEach {
            addHeaderRow(it, tableRowHeader, Color.BLACK)
        }

        for (i in 0 until transactions.size) {
//          TableRow.LayoutParams = new TableRow.LayoutParams (TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT,1.0f)
            val product: Transaction = transactions[i]
            val trProduct = TableRow(this)
//          trProduct.setBackgroundResource(R.drawable.table_row_bg1)
            addRow("Rs. " + product.amount, trProduct, Color.BLACK)
            addRow(""+product.notes_100, trProduct, Color.BLACK)
            addRow("" + product.notes_200, trProduct, Color.BLACK)
            addRow("" + product.notes_500, trProduct, Color.BLACK)
            addRow("" + product.notes_2000, trProduct, Color.BLACK)
            dataBinding.tableMain.addView(trProduct, layoutParams)
        }
    }

    private fun addHeaderRow(str: String, row: TableRow, color: Int) {
        val textView = TextView(this)
        textView.setTextColor(color)
        textView.text = str
        textView.setPadding(16, 5, 16, 5)
        textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
        row.addView(textView)
    }
    private fun addRow(str: String, row: TableRow, color: Int) {
        val textView = TextView(this)
        textView.setTextColor(color)
        textView.text = str
        textView.setPadding(16, 5, 16, 5)
        textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
        row.addView(textView)
    }

    private fun calculate(amountWithdraw:Int){
        withdrawal =amountWithdraw
        val notes = intArrayOf(2000, 500, 200, 100)
        val noteCounter = IntArray(notes.size)
        if (avaliable >= withdrawal) {
            for (i in 0 until notes.size) {
                if (amountWithdraw >= notes[i]) {
                    noteCounter[i] = withdrawal / notes[i]
                    withdrawal -= noteCounter[i] * notes[i]
                }
            }
            // Print notes
            println("Currency Count ->")
            for (i in 0 until notes.size) {
                if (noteCounter[i] != 0) {
                    Log.d("TAG", "calculate: "+notes[i].toString() + " : "
                            + noteCounter[i])
                }
            }
            val stamp=   TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()).toString()
            Log.d("TAG", "calculate: $stamp")
            val txn=Transaction(amountWithdraw,noteCounter[3],noteCounter[2],noteCounter[1],noteCounter[0],
                TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()).toString())
            viewModel.insert(txn)
            baseAmount(withdrawal,noteCounter)
        } else {
            Log.d("TAG", "calculate: you have insufficent amount")
        }
        getTxnList()

    }

    private fun baseAmount(withdrawal: Int, noteCounter: IntArray) {
        avaliable -=withdrawal
        tot_100-=noteCounter[3]
        tot_200-=noteCounter[2]
        tot_500-=noteCounter[1]
        tot_2000-=noteCounter[0]
        dataBinding.tvTotAmount.text= "$avaliable"
        dataBinding.tvNotes100.text="$tot_100"
        dataBinding.tvNotes200.text="$tot_200"
        dataBinding.tvNotes500.text="$tot_500"
        dataBinding.tvNotes2000.text="$tot_2000"
    }

    private fun getTxnList(){
        showHideProgress(true)
        viewModel.transactions.observe(this, Observer {
            showHideProgress(false)
            it?.let {
                if(it.isNotEmpty()){
                    dataBinding.tvTxnHeader.visibility=View.VISIBLE
                    dataBinding.cardAllTxn.visibility=View.VISIBLE
                    initTxnViews(it as ArrayList<Transaction>)
                    lastTxn()
                }else{
                    dataBinding.tvTxnHeader.visibility=View.VISIBLE
                    dataBinding.cardAllTxn.visibility=View.GONE
                }

            }
        })
    }

    private fun lastTxn(){
        showHideProgress(true)
        viewModel.lastTransaction.observe(this, Observer {
            showHideProgress(false)
            it?.let {
                dataBinding.tvLastTxnHeader.visibility=View.VISIBLE
                dataBinding.cardViewLastTxn.visibility=View.VISIBLE
                dataBinding.tvLastTxnTotAmount.text="${it.amount}"
                dataBinding.tvLastTxnNotes100.text="${it.notes_100}"
                dataBinding.tvLastTxnNotes200.text="${it.notes_200}"
                dataBinding.tvLastTxnNotes500.text="${it.notes_500}"
                dataBinding.tvLastTxnNotes2000.text="${it.notes_2000}"

            }
        })
    }




}