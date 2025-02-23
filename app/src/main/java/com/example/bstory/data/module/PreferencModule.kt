package com.example.bstory.data.module

import com.example.bstory.config.PreferenceConfig
import org.koin.dsl.module

val preferenceModule = module {
    single { PreferenceConfig(get()) }
}