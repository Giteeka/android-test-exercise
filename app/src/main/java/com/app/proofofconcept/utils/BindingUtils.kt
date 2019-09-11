package com.app.proofofconcept.utils

import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.app.proofofconcept.GlideApp
import com.app.proofofconcept.R

@BindingAdapter("setImagePath")
fun AppCompatImageView.setImagePath(path: String?) {
        GlideApp.with(this.context).load(path).placeholder(R.drawable.ic_image_black_24dp).into(this)
}

@BindingAdapter("setRefreshing")
fun SwipeRefreshLayout.setRefreshing(boolean: Boolean) {
    isRefreshing = boolean
}

@BindingAdapter(value = ["rowText", "defaultText"], requireAll = false)
//    @BindingAdapter("defaultText")
fun setTextViewText(view: TextView, rowText: String?, defaultText: String?) {
    Logg.e("BindingAdapter", "text : $rowText")
    Logg.e("BindingAdapter", "defaultText : $defaultText")
    if (rowText.isNullOrEmpty() ||rowText.equals("null", ignoreCase = false))
        view.text = defaultText
    else
        view.text = rowText

}

