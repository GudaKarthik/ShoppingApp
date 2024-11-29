package com.example.shoppingapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shoppingapp.dataclass.FavProducts
import kotlinx.coroutines.flow.Flow

@Dao
interface ShopDao {

    // Adding Item
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addItem(favproduct : FavProducts)

    // Accessing Fav Products
    @Query("select * from favproducts")
    fun getFavProducts() : Flow<List<FavProducts>>

    // Deleting Item
    @Query("DELETE FROM favproducts WHERE id = :id")
    fun deleteProduct(id: String)

}