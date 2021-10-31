package com.example.maneketech.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerAdapter<T : Any, DB : ViewDataBinding> :
    RecyclerView.Adapter<BaseRecyclerAdapter.Companion.BaseViewHolder<DB>>() {

    private var items = mutableListOf<T>()
    protected lateinit var dataBinding: DB


    companion object {
        class BaseViewHolder<DB : ViewDataBinding>(binding: DB) :
            RecyclerView.ViewHolder(binding.root)
    }

    abstract fun getLayoutInflater(): Int

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<DB> {
        dataBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            getLayoutInflater(),
            parent,
            false
        )
        return BaseViewHolder(dataBinding)
    }
}