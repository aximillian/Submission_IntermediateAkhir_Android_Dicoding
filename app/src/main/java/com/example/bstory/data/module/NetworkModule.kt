package com.example.bstory.data.module

import com.example.bstory.BuildConfig
import com.example.bstory.config.AppConfig
import com.example.bstory.config.PreferenceConfig
import com.example.bstory.domain.auth.AuthInterceptor
import com.example.bstory.domain.auth.AuthService
import com.example.bstory.domain.story.StoryService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val loggingInterceptor = if (BuildConfig.DEBUG) {
    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
} else {
    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
}

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(getHeaderInterceptor(get()))
            .addInterceptor(loggingInterceptor)
            .build()
    }
    single {
        Retrofit.Builder()
            .baseUrl(AppConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
    }
    single { provideAuthService(get()) }
    single { provideStoryService(get()) }
}

private fun getHeaderInterceptor(preferenceConfig: PreferenceConfig): Interceptor {
    val headers = HashMap<String, String>()
    headers["Content-Type"] = "application/json"

    return AuthInterceptor(headers, preferenceConfig)
}

fun provideAuthService(retrofit: Retrofit): AuthService = retrofit.create(AuthService::class.java)

fun provideStoryService(retrofit: Retrofit): StoryService = retrofit.create(StoryService::class.java)