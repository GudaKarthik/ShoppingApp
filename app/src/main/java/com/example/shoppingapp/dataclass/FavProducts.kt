package com.example.shoppingapp.dataclass

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class FavProducts(var productid : String,
                       var title : String,
                       var image : String,
                       @PrimaryKey(autoGenerate = true) var id : Int? = null)