package com.example.shoppingapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shoppingapp.R
import com.example.shoppingapp.dataclass.FavProducts

class FavProductsAdapter(var favlist : List<FavProducts>,var context: Context,var onClickFavProduct: onClickFavProduct) : RecyclerView.Adapter<FavProductsAdapter.FavViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.fav_item,parent,false)
        return FavViewHolder(view)
    }

    override fun getItemCount(): Int {
        return favlist.size
    }

    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {
        var favitem = favlist[position]
        holder.txtTitle.text = favitem.title.substring(0,16)
        Glide.with(context)
            .load(favitem.image)
            .into(holder.imgProduct)
            .view

        holder.itemView.setOnClickListener {
            onClickFavProduct.onClickFav(favitem)
        }
    }


    class FavViewHolder(view : View) : RecyclerView.ViewHolder(view){
        var txtTitle : TextView = view.findViewById(R.id.txtProductName)
        var imgProduct : ImageView = view.findViewById(R.id.imgProduct)
    }

}

interface onClickFavProduct{
    fun onClickFav(favItem : FavProducts)
}