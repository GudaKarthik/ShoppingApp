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
import com.example.shoppingapp.dataclass.DashboardCategories

class DashboardCategoryAdapter(var categorylist : ArrayList<DashboardCategories>,var context: Context,var onClickDashboard: onClickDashboard) : RecyclerView.Adapter<DashboardCategoryAdapter.DashboardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.category_item,parent,false)
        return DashboardViewHolder(view)
    }

    override fun getItemCount(): Int {
        return categorylist.size
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        var category = categorylist[position]
        holder.txtTitle.text = category.title

        Glide.with(context)
            .load(category.image)
            .into(holder.img)
            .view

        holder.itemView.setOnClickListener {
            onClickDashboard.onClickDashboard(category)
        }
    }

    class DashboardViewHolder(view : View) : RecyclerView.ViewHolder(view){
        var img : ImageView = view.findViewById(R.id.categoryicon)
        var txtTitle : TextView = view.findViewById(R.id.categorytitle)
    }
}

interface onClickDashboard{
    fun onClickDashboard(data : DashboardCategories)
}