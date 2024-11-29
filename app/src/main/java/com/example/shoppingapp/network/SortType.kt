package com.example.shoppingapp.network

sealed class SortType {

    object Ascending : SortType()
    object Descending : SortType()
}