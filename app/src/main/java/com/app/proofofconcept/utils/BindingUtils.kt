package com.app.proofofconcept.utils

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.app.proofofconcept.GlideApp

@BindingAdapter("setImagePath")
fun AppCompatImageView.setImagePath(path: String?) {
    if (path.isNullOrBlank()) {
        GlideApp.with(this.context).load(this.drawable).into(this)
    } else {
        GlideApp.with(this.context).load(path).into(this)
    }

    @BindingAdapter("setRefreshing")
    fun SwipeRefreshLayout.setRefreshing(boolean: Boolean) {
        isRefreshing = boolean
    }

}