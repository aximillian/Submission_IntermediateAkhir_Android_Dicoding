package com.example.bstory.domain.auth

import com.example.bstory.data.response.ApiResponse
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun login(dto: LoginRequest): Flow<ApiResponse<LoginResponse>>

    fun register(dto: RegisterRequest): Flow<ApiResponse<RegisterResponse>>

    fun logout(): Boolean
}