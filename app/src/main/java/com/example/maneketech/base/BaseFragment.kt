package com.example.maneketech.base

import android.app.Dialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.maneketech.R
import com.google.android.material.snackbar.Snackbar


abstract class BaseFragment<DB : ViewDataBinding, VM : ViewModel> : Fragment() {

    protected lateinit var dataBinding: DB
    protected lateinit var viewModel: VM
    lateinit var dialog : Dialog
    abstract fun getLayoutID(): Int
    abstract fun getViewModel(): Class<VM>
//    abstract fun getBaseContext(): Context?



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        dataBinding = DataBindingUtil.inflate(inflater, getLayoutID(), container, false)
        dataBinding.lifecycleOwner = this
        viewModel = ViewModelProvider(this).get(getViewModel())
        dialog = activity?.let { Dialog(it) }!!
        return dataBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    fun showSuccessToast(message: String, context: Context) {
        Toast.makeText(context,message,Toast.LENGTH_LONG).show()

    }

    fun showErrorToast(message: String, context: Context) {

        Toast.makeText(context,message,Toast.LENGTH_LONG).show()
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

    open fun showHideProgress(isShow: Boolean,mContext: Context) {
        val view: View =
            LayoutInflater.from(mContext).inflate(R.layout.progress_dialog, null, false)
        dialog.setContentView(view)
        if (isShow) {
            if (!dialog.isShowing()) dialog.show()
        } else {
            if (dialog.isShowing()) {
                dialog.dismiss()
            }
        }
    }
   open fun  showToast( root: View,message: String){
        Snackbar.make(root,message,Snackbar.LENGTH_LONG).show()
    }

}