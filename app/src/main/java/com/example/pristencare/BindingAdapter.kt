package com.example.pristencare

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("app:loadUrl")
fun ImageView.loadUrl(photo: Photo) {
    val url =
        "https://live.staticflickr.com" + photo.server + "/" + photo.id + "_" + photo.secret + "_" + "w.jpg"
    Glide.with(this.context).load(url).into(this)

}