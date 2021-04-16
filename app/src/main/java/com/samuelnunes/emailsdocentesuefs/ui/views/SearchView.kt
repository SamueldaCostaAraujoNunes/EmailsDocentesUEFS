package com.samuelnunes.emailsdocentesuefs.ui.views

import android.animation.Animator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.widget.FrameLayout
import com.samuelnunes.emailsdocentesuefs.databinding.WidgetSearchViewBinding

class SearchView(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {
    val binding: WidgetSearchViewBinding

    init {
        val inflater = LayoutInflater.from(context)
        binding = WidgetSearchViewBinding.inflate(inflater, this, true)
    }

    fun openSearch() {
        binding.searchBar.visibility = View.VISIBLE
        val reveal = ViewAnimationUtils.createCircularReveal(
            binding.searchBar,
            (binding.btnSearch.right + binding.btnSearch.left) / 2,
            (binding.btnSearch.top + binding.btnSearch.bottom) / 2,
            0f, width.toFloat()
        )
        reveal.duration = 300
        reveal.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) = Unit
            override fun onAnimationCancel(animation: Animator?) = Unit
            override fun onAnimationStart(animation: Animator?) = Unit
            override fun onAnimationEnd(animation: Animator?) {
                binding.inputSearch.requestFocus()
            }
        })
        reveal.start()
    }

    fun closeSearch() {
        val conceal = ViewAnimationUtils.createCircularReveal(
            binding.searchBar,
            (binding.btnSearch.right + binding.btnSearch.left) / 2,
            (binding.btnSearch.top + binding.btnSearch.bottom) / 2,
            width.toFloat(), 0f
        )

        conceal.duration = 300
        conceal.start()
        conceal.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) = Unit
            override fun onAnimationCancel(animation: Animator?) = Unit
            override fun onAnimationStart(animation: Animator?) = Unit
            override fun onAnimationEnd(animation: Animator?) {
                binding.searchBar.visibility = View.INVISIBLE
                binding.inputSearch.setText("")
                conceal.removeAllListeners()
            }
        })
    }

    fun openSearchWithoutAnimation() {
        binding.searchBar.visibility = View.VISIBLE
    }

    fun closeSearchWithoutAnimation() {
        binding.searchBar.visibility = View.INVISIBLE
    }
}