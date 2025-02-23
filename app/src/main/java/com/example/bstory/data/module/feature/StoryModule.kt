package com.example.bstory.data.module.feature

import com.example.bstory.domain.story.StoryRepository
import com.example.bstory.domain.story.StoryRepositoryImpl

import org.koin.dsl.module

val storyModule = module {
    single<StoryRepository>  { StoryRepositoryImpl(get(), get()) }
}