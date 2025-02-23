package com.example.bstory.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bstory.data.response.ApiResponse
import com.example.bstory.data.response.DetailResponse
import com.example.bstory.domain.auth.AuthRepository
import com.example.bstory.domain.auth.RegisterRequest
import kotlinx.coroutines.launch

class RegisterViewModel(private val authRepository: AuthRepository) : ViewModel() {
    private val _registerResult = MutableLiveData<ApiResponse<DetailResponse>>()
    val registerResult: LiveData<ApiResponse<DetailResponse>> by lazy { _registerResult }

    fun register(name: String, email: String, password: String) {
        viewModelScope.launch {
            authRepository.register(RegisterRequest(name, email, password)).collect {
                _registerResult.value = it
            }
        }
    }
}