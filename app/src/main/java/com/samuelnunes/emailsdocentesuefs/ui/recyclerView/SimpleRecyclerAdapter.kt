package com.abed.myapplication.recyclerView

import android.view.View
import android.view.animation.AnimationUtils
import androidx.annotation.LayoutRes

class SimpleRecyclerAdapter<RecyclerData : Any>(
    data: List<RecyclerData>, @LayoutRes layoutID: Int, action: (data: RecyclerData) -> Unit,
    private val onBindView: BaseViewHolder<RecyclerData>.(data: RecyclerData) -> Unit
) : BaseRecyclerAdapter<RecyclerData>(data, action) {

    override val layoutItemId: Int = layoutID

    override fun onBindViewHolder(holder: BaseViewHolder<RecyclerData>, position: Int) {
        holder.onBindView(dataList[position])
        holder.setOnClickListener(dataList[position])
    }
}
