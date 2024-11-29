package com.example.shoppingapp.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shoppingapp.R
import com.example.shoppingapp.dataclass.FavProducts
import com.example.shoppingapp.dataclass.LimitedProducts
import com.example.shoppingapp.dataclass.Products
import com.example.shoppingapp.viewmodel.FavProductsViewModel

class AllProductsAdapter(var productslist : ArrayList<LimitedProducts>,var context: Context,var onClickProduct: onClickProduct,var favProducts: List<FavProducts>) : RecyclerView.Adapter<AllProductsAdapter.ProductViewHolder>() {

    var favID : String? = null
    var TAG : String = "ShopKart"
    var favProductList : List<FavProducts>? = null

    fun addItem(item : List<LimitedProducts>){
        this.productslist = item.toMutableList() as ArrayList<LimitedProducts>
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.product_item,parent,false)
        return ProductViewHolder(view)
    }

    override fun getItemCount(): Int {
        return productslist.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        var product = productslist[position]
        var title = product.title!!.subSequence(0,14)
        holder.txtTitle.text = title
    //    holder.txtPrice.text = "₹ " + product.price.toString()

        Glide.with(context)
            .load(product.image)
            .into(holder.imgProduct)
            .view

        var simage : String? = null
        var productid : String? = null

        favProducts.filter {
            it.image!!.contains(product.image!!)
        }.map {
            simage = it.image
            productid = it.id.toString()
            holder.imgnotFav.visibility = View.VISIBLE
            holder.imgFav.visibility = View.GONE
        }

        /*if (simage.isNullOrEmpty()){
            holder.imgFav.visibility = View.VISIBLE
            holder.imgnotFav.visibility = View.GONE
            holder.txtPrice.text = "₹ " + product.price.toString() + " Not Saved"
        }else{
            holder.imgnotFav.visibility = View.VISIBLE
            holder.imgFav.visibility = View.GONE
            holder.txtPrice.text = "₹ " + product.price.toString() + " Saved"
        }*/

        holder.imgFav.setOnClickListener {
            holder.imgFav.visibility = View.GONE
            holder.imgnotFav.visibility = View.VISIBLE
            onClickProduct.onClickFav(product)
        }

        holder.imgnotFav.setOnClickListener {
            holder.imgnotFav.visibility = View.GONE
            holder.imgFav.visibility = View.VISIBLE
            onClickProduct.onRemoveFav(product,productid.toString())
        }

        holder.itemView.setOnClickListener {
            onClickProduct.clickProduct(product)
        }


    }


    class ProductViewHolder(view : View) : RecyclerView.ViewHolder(view){
        var txtTitle : TextView = view.findViewById(R.id.txtTitle)
        var imgProduct : ImageView = view.findViewById(R.id.imgProduct)
        var txtPrice : TextView = view.findViewById(R.id.txtPrice)
        var imgFav : ImageView = view.findViewById(R.id.imgFavourite)
        var imgnotFav : ImageView = view.findViewById(R.id.imgNotfav)
    }
}

interface onClickProduct{
    fun clickProduct(data : LimitedProducts)

    fun onClickFav(data: LimitedProducts)

    fun onRemoveFav(data: LimitedProducts,prodId : String)
}