package com.example.bstory.data.module.feature

import com.example.bstory.domain.auth.AuthRepository
import com.example.bstory.domain.auth.AuthRepositoryImpl
import org.koin.dsl.module

val authModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
}