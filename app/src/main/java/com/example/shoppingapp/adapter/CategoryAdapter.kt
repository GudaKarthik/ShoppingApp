package com.example.shoppingapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingapp.R
import com.example.shoppingapp.dataclass.Categories
import com.example.shoppingapp.dataclass.Sorts

class CategoryAdapter(var sorts: ArrayList<Sorts>, var context: Context, var onClickFilter: onClickFilter) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.filters_item,parent,false)
        return CategoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return sorts.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        var sort = sorts[position]
        holder.txtTitle.text = sort.name
        holder.itemView.setOnClickListener {
            onClickFilter.clickFilter(sort)
        }
    }

    class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var txtTitle : TextView = view.findViewById(R.id.txtCategory)
    }
}

interface onClickFilter{
    fun clickFilter(data : Sorts)
}