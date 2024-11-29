package com.example.shoppingapp.usecase

import android.util.Log
import com.example.shoppingapp.dataclass.LimitedProducts
import com.example.shoppingapp.network.NetworkResult
import com.example.shoppingapp.repository.ShopRepository
import com.example.shoppingapp.retrofit.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetLimitedProducts
@Inject
constructor(var apiService: ApiService) {

     fun invoke(limit : String)  = flow {
         var limitedproducts = apiService.getLimitedProducts(limit)
         emit(NetworkResult.Loading(true))
         if (limitedproducts.isSuccessful){
             Log.d("ShopKart","The usecase is ${limitedproducts.body()}")
             emit(NetworkResult.Success(limitedproducts.body()!!))
         }else{
             emit(NetworkResult.Error(limitedproducts.message()))
         }
    }.catch {
        emit(NetworkResult.Failure(it.message.toString()))
     }
}