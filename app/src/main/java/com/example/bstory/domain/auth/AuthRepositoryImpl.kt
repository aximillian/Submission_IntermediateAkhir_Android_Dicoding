package com.example.bstory.domain.auth

import com.example.bstory.config.AppConfig
import com.example.bstory.config.PreferenceConfig
import com.example.bstory.data.response.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.context.GlobalContext.unloadKoinModules
import org.koin.core.context.loadKoinModules
import java.lang.Exception

class AuthRepositoryImpl(
    private val authService: AuthService,
    private val preferenceConfig: PreferenceConfig
) : AuthRepository {
    override fun login(dto: LoginRequest): Flow<ApiResponse<LoginResponse>> = flow {
        try {
            emit(ApiResponse.Loading)
            val response = authService.login(dto.email, dto.password)
            if (response.error) {
                emit(ApiResponse.Error(response.message))
            } else {
                val loginResult = response.loginResult
                preferenceConfig.setLoginPrefs(loginResult)
                reloadKoinModules()
                emit(ApiResponse.Success(response))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(ApiResponse.Error(e.message.toString()))
        }
    }

    override fun register(dto: RegisterRequest): Flow<ApiResponse<RegisterResponse>> = flow {
        try {
            emit(ApiResponse.Loading)
            val response = authService.register(dto.name, dto.email, dto.password)
            if (response.error) {
                emit(ApiResponse.Error(response.message))
            } else {
                emit(ApiResponse.Success(response))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(ApiResponse.Error(e.message.toString()))
        }
    }

    override fun logout(): Boolean {
        return try {
            preferenceConfig.clearAllPreferences()
            reloadKoinModules()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    private fun reloadKoinModules() {
        unloadKoinModules(AppConfig.koinModules)
        loadKoinModules(AppConfig.koinModules)
    }
}