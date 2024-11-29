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
import com.example.shoppingapp.dataclass.LimitedProducts

class LimitedProductsAdapter(var limitedlist : ArrayList<LimitedProducts>, var context: Context,var onClickLimitedProduct: onClickLimitedProduct) : RecyclerView.Adapter<LimitedProductsAdapter.LimitedViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LimitedViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.limitedproduct_item,parent,false)
        return LimitedViewHolder(view)
    }

    override fun getItemCount(): Int {
        return limitedlist.size
    }

    override fun onBindViewHolder(holder: LimitedViewHolder, position: Int) {
        var limitedproductitem = limitedlist[position]
        Glide.with(context)
            .load(limitedproductitem.image.toString())
            .into(holder.imgLimitedProduct)
            .view

        holder.txtProductname.text = limitedproductitem.title
        holder.txtProductPrice.text = "For ₹" + limitedproductitem.price.toString()
        holder.itemView.setOnClickListener {
            onClickLimitedProduct.onClick(limitedproductitem)
        }
    }

    class LimitedViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var imgLimitedProduct : ImageView = view.findViewById(R.id.imgLimitedProduct)
        var txtProductname : TextView = view.findViewById(R.id.txtProductName)
        var txtProductPrice : TextView = view.findViewById(R.id.txtProdcutPrice)
    }
}

interface onClickLimitedProduct{
    fun onClick(data : LimitedProducts)
}