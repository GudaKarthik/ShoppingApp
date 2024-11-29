package com.example.shoppingapp.data

import android.content.Context
import android.widget.Toast

class Constants {
    companion object{
        const val TOKEN : String = "Login_Token"
        const val ASC : String = "asc"
        const val DESC : String = "desc"
        const val CATEGORY : String = "category"
        const val ELECTRONICS : String = "electronics"
        const val JEWELLERY : String = "jewelery"
        const val MENCLOTHING : String = "men's clothing"
        const val WOMENCLOTHING : String = "women's clothing"

        fun responseCode(context : Context,statuscode : Int){
            when(statuscode){
                401 ->{
                    Toast.makeText(context,"Username or password is incorrect",Toast.LENGTH_SHORT).show()
                }
                400 ->{
                    Toast.makeText(context,"Enter username and password",Toast.LENGTH_SHORT).show()
                }

            }
        }
    }
}