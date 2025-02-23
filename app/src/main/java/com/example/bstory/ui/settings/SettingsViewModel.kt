package com.example.bstory.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bstory.domain.auth.AuthRepository
import com.example.bstory.domain.auth.AuthRepositoryImpl

class SettingsViewModel(private val authRepository: AuthRepository) : ViewModel() {

    fun logout() = authRepository.logout()
}