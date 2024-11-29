package com.example.shoppingapp.database

import androidx.room.Database
import androidx.room.Entity
import androidx.room.RoomDatabase
import com.example.shoppingapp.dataclass.FavProducts

@Database(entities = [FavProducts::class], version = 1)
abstract class DataBase : RoomDatabase() {

    abstract val shopDao : ShopDao

    companion object{
        const val DATABASE_NAME = "ShopKart"
    }
}