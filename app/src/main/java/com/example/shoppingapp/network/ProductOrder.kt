package com.example.shoppingapp.network

sealed class ProductOrder(var sortType: SortType) {

    class ID(sortType: SortType) : ProductOrder(sortType)
    class ProductID(sortType: SortType) : ProductOrder(sortType)
    class ProductTitle(sortType: SortType) : ProductOrder(sortType)
    class ProductImage(sortType: SortType) : ProductOrder(sortType)

}