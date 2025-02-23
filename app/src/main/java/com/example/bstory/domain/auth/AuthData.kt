package com.example.bstory.domain.auth

import com.example.bstory.data.response.DetailResponse
import com.google.gson.annotations.SerializedName

// Register Data Class
data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String
)

class RegisterResponse : DetailResponse()


// Login Data Class
data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    @field:SerializedName("loginResult")
    val loginResult: LoginResult
) : DetailResponse()

data class LoginResult(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("userId")
    val userId: String? = null,

    @field:SerializedName("token")
    val token: String? = null
)

