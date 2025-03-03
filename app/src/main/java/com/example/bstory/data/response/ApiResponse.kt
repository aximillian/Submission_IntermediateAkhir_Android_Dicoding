package com.example.bstory.data.response

sealed class ApiResponse<out R> {

    data class Success<out T>(val data: T) : ApiResponse<T>()

    data class Error(val errorMessage: String) : ApiResponse<Nothing>()

    data object Loading : ApiResponse<Nothing>()
}