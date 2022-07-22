package com.gibox.moviku.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.gibox.moviku.R

@BindingAdapter("loadImage")
fun loadImage(imageView: ImageView, url: String) {
    url.let {
        Glide.with(imageView)
            .load(url)
            .placeholder(R.drawable.ic_cinema_alt_svgrepo_com)
            .error(R.drawable.ic_cinema_alt_svgrepo_com)
            .into(imageView)
    }
}