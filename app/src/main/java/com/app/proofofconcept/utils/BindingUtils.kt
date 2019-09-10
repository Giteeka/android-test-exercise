package com.app.proofofconcept.utils

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.app.proofofconcept.GlideApp

@BindingAdapter("setImagePath")
fun AppCompatImageView.setImagePath(path: String?) {
    Logg.e("Binding", "path : $path")
    if (path.isNullOrBlank()) {
        GlideApp.with(this.context).load(this.drawable).into(this)
    } else {
        GlideApp.with(this.context).load(path).into(this)
    }
}