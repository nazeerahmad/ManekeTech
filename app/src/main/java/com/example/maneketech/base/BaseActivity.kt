package com.example.maneketech.base

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.pm.ActivityInfo
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.maneketech.R

import com.google.android.material.snackbar.Snackbar


abstract class BaseActivity<DB : ViewDataBinding, VM : ViewModel> : AppCompatActivity() {

    protected lateinit var dataBinding: DB
    protected lateinit var viewModel: VM

    abstract fun getLayoutId(): Int
    abstract fun getViewModel(): Class<VM>
    lateinit var dialog :Dialog
    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, getLayoutId())
        dataBinding.lifecycleOwner = this
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        viewModel = ViewModelProvider(this).get(getViewModel())
        dialog = Dialog(this)
    }

    fun showSuccessToast(message: String) {
        Toast.makeText(this,message, Toast.LENGTH_LONG).show()
    }

    open fun showErrorToast(message: String) {

        Toast.makeText(this,message,Toast.LENGTH_LONG).show()
    }

    fun isNetworkAvailable(context: Context?): Boolean {
        if (context == null) return false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }

    open fun showHideProgress(isShow: Boolean) {
        val view: View =
            LayoutInflater.from(this).inflate(R.layout.progress_dialog, null, false)
        dialog.setContentView(view)
        if (isShow) {
            if (!dialog.isShowing()) dialog.show()
        } else {
            if (dialog.isShowing()) {
                dialog.dismiss()
            }
        }
    }

   fun showSnackBar(root : View,message: String){
       Snackbar.make(root,message,Snackbar.LENGTH_LONG).show()
   }

}