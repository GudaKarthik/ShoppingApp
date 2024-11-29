package com.example.shoppingapp.retrofit

import androidx.lifecycle.LiveData
import com.example.shoppingapp.dataclass.LimitedProducts
import com.example.shoppingapp.dataclass.LoginRequest
import com.example.shoppingapp.dataclass.LoginResponse
import com.example.shoppingapp.dataclass.ProfileResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest) : Response<LoginResponse>

    // Limit Results
    @GET("products")
    suspend fun getLimitedProducts(@Query("limit") limit : String) : Response<ArrayList<LimitedProducts>>

    // All Products
    @GET("products")
    suspend fun getAllProducts(@Query("sort") sort : String) : Response<ArrayList<LimitedProducts>>

    // Get Product Profile
    @GET("products/2")
    suspend fun getProductProfile() : Response<ProfileResponse>

    // Choose Category
    @GET("products/category/{categoryType}")
    suspend fun getCategoryProducts(@Path("categoryType") category : String) : Response<ArrayList<LimitedProducts>>

    // Single Product
    @GET("products/{id}")
    suspend fun getSingleLimitedProduct(@Path("id") id : String) : Response<LimitedProducts>

}