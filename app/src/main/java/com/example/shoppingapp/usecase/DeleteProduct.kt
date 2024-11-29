package com.example.shoppingapp.usecase

import com.example.shoppingapp.database.ShopDao
import com.example.shoppingapp.dataclass.FavProducts
import com.example.shoppingapp.repository.ShopRepository

class DeleteProduct(var shopRepository: ShopRepository) {

    operator fun invoke(favProducts: FavProducts,id : String){
        shopRepository.deleteProduct(favProducts,id)
    }
}