package com.abed.myapplication.recyclerView

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerAdapter<Data : Any>(
    protected var dataList: List<Data>
) : RecyclerView.Adapter<BaseViewHolder<Data>>() {

    abstract val layoutItemId: Int

    override fun getItemCount(): Int = dataList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Data> =
        BaseViewHolder(
            parent,
            layoutItemId
        )
}