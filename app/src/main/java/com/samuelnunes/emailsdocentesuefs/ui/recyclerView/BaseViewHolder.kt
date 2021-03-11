package com.abed.myapplication.recyclerView

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer

/**
 * Created by ${User} on ${Date}
 */
open class BaseViewHolder<in T>(parent: ViewGroup, @LayoutRes layoutId: Int, private val action: (data: T) -> Unit) :
    RecyclerView.ViewHolder(parent.inflater(layoutId)), LayoutContainer {
    override val containerView: View?
        get() = itemView

    fun setOnClickListener(data: T) {
        itemView.setOnClickListener{ action(data) }
    }
}