package com.example.shoppingapp.module

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import com.example.shoppingapp.database.DataBase
import com.example.shoppingapp.database.ShopDao
import com.example.shoppingapp.repository.ShopRepository
import com.example.shoppingapp.repository.ShoppingRepository
import com.example.shoppingapp.repository.ShoppingRepositoryImpl
import com.example.shoppingapp.retrofit.ApiService
import com.example.shoppingapp.usecase.DeleteProduct
import com.example.shoppingapp.usecase.GetAllProducts
import com.example.shoppingapp.usecase.GetFavProducts
import com.example.shoppingapp.usecase.GetLimitedProducts
import com.example.shoppingapp.usecase.GetSingleLimitedProduct
import com.example.shoppingapp.usecase.GetUserProfile
import com.example.shoppingapp.usecase.InsertProduct
import com.example.shoppingapp.util.AllProductsUseCase
import com.example.shoppingapp.util.FavProductsUsecase
import com.example.shoppingapp.util.LimitedProductUsecase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ShopModule {

    @Singleton
    @Provides
    fun provideRetrofit() : Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://fakestoreapi.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit) : ApiService{
        return retrofit.create(ApiService::class.java)
    }

    /*@Singleton
    @Provides
    fun provideRepositoryImpl(apiService: ApiService) : ShopRepository{
        return ShoppingRepositoryImpl(apiService)
    }*/

    @Singleton
    @Provides
    fun provideLimitedProducts(apiService: ApiService) : LimitedProductUsecase{
        return LimitedProductUsecase(getLimitedProducts = GetLimitedProducts(apiService)
        , getSingleProduct = GetSingleLimitedProduct(apiService)
        )
    }

    @Singleton
    @Provides
    fun provideAllProducts(apiService: ApiService) : AllProductsUseCase{
        return AllProductsUseCase(getAllProducts = GetAllProducts(apiService))
    }

    @Singleton
    @Provides
    fun getUserProfile(apiService: ApiService) : GetUserProfile{
        return GetUserProfile(apiService)
    }

    @Singleton
    @Provides
    fun provideDatabase(app : Application) : DataBase {
        return Room.databaseBuilder(app,DataBase::class.java,DataBase.DATABASE_NAME).build()
    }

    @Singleton
    @Provides
    fun provideRepository(db : DataBase) : ShopRepository{
        return ShoppingRepositoryImpl(db.shopDao)
    }

    @Singleton
    @Provides
    fun provideFavProductUsecase(shopRepository: ShopRepository) : FavProductsUsecase{
        return FavProductsUsecase(InsertProduct(shopRepository),
            GetFavProducts(shopRepository), deleteProduct = DeleteProduct(shopRepository)
        )
    }


}