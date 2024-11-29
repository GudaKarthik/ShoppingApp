package com.example.shoppingapp.usecase

import android.util.Log
import com.example.shoppingapp.dataclass.LimitedProducts
import com.example.shoppingapp.network.NetworkResult
import com.example.shoppingapp.retrofit.ApiService
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class GetAllProducts
@Inject
constructor(var apiService: ApiService){

    operator fun invoke(sort : String, category : String) = flow {
        var response : Response<ArrayList<LimitedProducts>>

        if (category == "All"){
            Log.d("ShopKart","The sort is $sort")
            response = apiService.getAllProducts(sort)
        }else{
            response = apiService.getCategoryProducts(category)
        }
        emit(NetworkResult.Loading(true))
        if (response.isSuccessful){
            emit(NetworkResult.Success(response.body()!!))
        }else{
            Log.d("ShopKart","The issue is ${response.message()}")
            emit(NetworkResult.Error(response.message()))
        }
    }.catch {
        emit(NetworkResult.Failure(it.message.toString()))
        Log.d("ShopKart","The issue is ${it.message}")
    }

}