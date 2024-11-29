package com.example.shoppingapp.usecase

import android.util.Log
import com.example.shoppingapp.dataclass.FavProducts
import com.example.shoppingapp.network.ProductOrder
import com.example.shoppingapp.network.SortType
import com.example.shoppingapp.repository.ShopRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetFavProducts(var shopRepository: ShopRepository) {

    operator fun invoke() : Flow<List<FavProducts>> {
        return shopRepository.getFavProducts()
    }
}