package com.example.bstory.config

import com.example.bstory.BuildConfig
import com.example.bstory.R
import com.example.bstory.data.module.databaseModule
import com.example.bstory.data.module.feature.authModule
import com.example.bstory.data.module.feature.storyModule
import com.example.bstory.data.module.networkModule
import com.example.bstory.data.module.preferenceModule
import com.example.bstory.data.module.viewModelModule

object AppConfig {

    // Splash Screen
    const val SPLASH_SCREEN_DURATION = 3

    // Navigation Bar Destination
    val navBarDestination = listOf(R.id.navigation_home , R.id.navigation_map, R.id.navigation_settings)

    // Shared Preferences
    const val PREFS_NAME = "bstory.pref"
    const val KEY_TOKEN = "key.token"
    const val KEY_NAME = "key.name"

    // Database
    const val DATABASE_NAME = "story_database"

    // Paging
    const val INITIAL_PAGE_INDEX = 1

    // Network
    const val BASE_URL = BuildConfig.BASE_URL

    // File
    const val MAXIMAL_SIZE = 1000000 // 1 MB
    const val FILENAME_FORMAT = "yyyyMMdd_HHmmss"

    // Injections Modules
    val koinModules = listOf(
        networkModule,
        preferenceModule,
        viewModelModule,
        databaseModule,
        authModule,
        storyModule,
    )
}