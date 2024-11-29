package com.example.shoppingapp.usecase

import com.example.shoppingapp.dataclass.FavProducts
import com.example.shoppingapp.repository.ShopRepository

class InsertProduct(var shopRepository: ShopRepository) {

    operator fun invoke(favproducts : FavProducts){
        shopRepository.insertFavProduct(favproducts)
    }
}