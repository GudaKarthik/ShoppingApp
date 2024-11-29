package com.example.shoppingapp.repository

import androidx.lifecycle.LiveData
import com.example.shoppingapp.database.ShopDao
import com.example.shoppingapp.dataclass.FavProducts
import com.example.shoppingapp.dataclass.LimitedProducts
import kotlinx.coroutines.flow.Flow

interface ShopRepository {

    fun insertFavProduct(favProducts: FavProducts)

    fun getFavProducts() : Flow<List<FavProducts>>

    fun deleteProduct(favProducts: FavProducts,id : String)

}