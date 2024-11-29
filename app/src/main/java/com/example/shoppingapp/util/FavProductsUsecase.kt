package com.example.shoppingapp.util

import com.example.shoppingapp.usecase.DeleteProduct
import com.example.shoppingapp.usecase.GetFavProducts
import com.example.shoppingapp.usecase.InsertProduct

data class FavProductsUsecase
    (var insertProduct: InsertProduct,
     var getFavProducts: GetFavProducts,
     var deleteProduct : DeleteProduct)