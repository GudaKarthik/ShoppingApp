package com.example.shoppingapp.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl")
fun loadImage(imageView: ImageView,url : String?){
    url?.let {
        Glide.with(imageView.context)
            .load(it)
            .into(imageView)
    }
}