package com.example.shoppingapp.usecase

import android.util.Log
import com.example.shoppingapp.network.NetworkResult
import com.example.shoppingapp.retrofit.ApiService
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class GetSingleLimitedProduct
@Inject
constructor(var apiService: ApiService){

    operator fun invoke(id : String) = flow {
        var singleresponse = apiService.getSingleLimitedProduct(id)
        emit(NetworkResult.Loading(true))
        if (singleresponse.isSuccessful) {
            emit(NetworkResult.Success(singleresponse.body()!!))
        }else{
            emit(NetworkResult.Error(singleresponse.message()))
        }
    }.catch {
        emit(NetworkResult.Failure(it.message.toString()))
        Log.d("ShopKart","The error message is ${it.message}")
    }
}