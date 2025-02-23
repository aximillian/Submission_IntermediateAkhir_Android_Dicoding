package com.example.bstory.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bstory.data.response.ApiResponse
import com.example.bstory.domain.auth.AuthRepository
import com.example.bstory.domain.auth.LoginRequest
import com.example.bstory.domain.auth.LoginResponse
import kotlinx.coroutines.launch

class LoginViewModel(private val authRepository: AuthRepository) : ViewModel() {
    private val _loginResult = MutableLiveData<ApiResponse<LoginResponse>>()
    val loginResult: LiveData<ApiResponse<LoginResponse>> by lazy { _loginResult }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            authRepository.login(LoginRequest(email, password)).collect {
                _loginResult.value = it
            }
        }
    }
}