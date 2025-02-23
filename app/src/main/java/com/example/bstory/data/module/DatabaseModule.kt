package com.example.bstory.data.module

import androidx.room.Room
import com.example.bstory.config.AppConfig
import com.example.bstory.data.response.StoryDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            StoryDatabase::class.java,
            AppConfig.DATABASE_NAME
        ).build()
    }

    single { get<StoryDatabase>().storyDao() }
    single { get<StoryDatabase>().remoteKeysDao() }
}