package com.example.shoppingapp.dataclass

data class LoginRequest(var username : String? = null,
                        var password : String? = null)

data class LoginResponse(var token : String)

data class DashboardCategories(var image: Int, var title: String)

class InvalidTripException(message : String) : Exception(message)