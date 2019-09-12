package com.app.proofofconcept.utils

import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.app.proofofconcept.GlideApp
import com.app.proofofconcept.R

/**
 * load image from network url
 * @receiver AppCompatImageView
 * @param path String?
 */
@BindingAdapter("setImagePath")
fun AppCompatImageView.setImagePath(path: String?) {
        GlideApp.with(this.context).load(path).placeholder(R.drawable.ic_image_black_24dp).into(this)
}

/**
 * handle refreshing view of swipe to refresh layout
 * @receiver SwipeRefreshLayout
 * @param boolean Boolean
 */
@BindingAdapter("setRefreshing")
fun SwipeRefreshLayout.setRefreshing(boolean: Boolean) {
    isRefreshing = boolean
}

/**
 * set default text of textview in case of null/empty data
 * @param view TextView
 * @param rowText String? actual text
 * @param defaultText String? default text
 */
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

