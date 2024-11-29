package com.example.shoppingapp.dataclass

import com.google.gson.annotations.SerializedName

data class LimitedProducts(@SerializedName("id" ) var id : Int?    = null,
                           @SerializedName("title" ) var title : String? = null,
                           @SerializedName("price" ) var price : Double? = null,
                           @SerializedName("description" ) var description : String? = null,
                           @SerializedName("category" ) var category : String? = null,
                           @SerializedName("image" ) var image : String? = null,
                           @SerializedName("rating" ) var rating : Rating? = Rating())

data class Rating(@SerializedName("rate" ) var rate : Double? = null,
                  @SerializedName("count" ) var count : Int? = null
)

data class Products(@SerializedName("id" ) var id : Int?    = null,
                    @SerializedName("title" ) var title : String? = null,
                    @SerializedName("price" ) var price : Double? = null,
                    @SerializedName("description" ) var description : String? = null,
                    @SerializedName("category" ) var category : String? = null,
                    @SerializedName("image" ) var image : String? = null,
                    @SerializedName("rating" ) var rating : Rating? = Rating())

data class Categories(var id : String,
                      var name : String)

data class Sorts(var id : String,
       var name : String)
