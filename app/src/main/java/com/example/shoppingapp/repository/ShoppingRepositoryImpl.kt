package com.example.shoppingapp.repository

import androidx.lifecycle.LiveData
import com.example.shoppingapp.database.ShopDao
import com.example.shoppingapp.dataclass.FavProducts
import com.example.shoppingapp.dataclass.LimitedProducts
import com.example.shoppingapp.dataclass.LoginRequest
import com.example.shoppingapp.dataclass.LoginResponse
import com.example.shoppingapp.retrofit.ApiService
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class ShoppingRepositoryImpl(var shopDao: ShopDao) : ShopRepository {
    override fun insertFavProduct(favProducts: FavProducts) {
        return shopDao.addItem(favProducts)
    }

    override fun getFavProducts(): Flow<List<FavProducts>> {
        return shopDao.getFavProducts()
    }

    override fun deleteProduct(favProducts: FavProducts,id : String) {
        return shopDao.deleteProduct(id)
    }

}