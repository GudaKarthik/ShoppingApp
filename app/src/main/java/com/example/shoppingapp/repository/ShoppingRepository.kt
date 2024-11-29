package com.example.shoppingapp.repository

import android.util.Log
import com.example.shoppingapp.dataclass.LoginRequest
import com.example.shoppingapp.dataclass.LoginResponse
import com.example.shoppingapp.network.NetworkResult
import com.example.shoppingapp.retrofit.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import retrofit2.http.Body
import javax.inject.Inject

class ShoppingRepository
@Inject
constructor(var apiService: ApiService){
     fun doLogin(username : String,password : String) = flow {

        if (username.isBlank()){
            emit(NetworkResult.Error("Please enter username"))
        }else if (password.isBlank()){
            emit(NetworkResult.Error("Please enter password"))
        }else {

            emit(NetworkResult.Loading(true))
            var login = LoginRequest(username, password)
            var response = apiService.login(login)

            if (response.isSuccessful){
                emit(NetworkResult.Success(response.body()!!))
            }else{
                Log.d("ShopKart","Issue is $response")
            }
            if (response.code() == 401){
                emit(NetworkResult.Error("Invalid Credentials"))
            }

        }
    }.catch { e->
        emit(NetworkResult.Failure(e.message ?: "Unknown message"))
    }

}