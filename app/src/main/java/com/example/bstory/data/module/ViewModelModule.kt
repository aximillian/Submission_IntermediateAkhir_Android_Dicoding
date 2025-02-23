package com.example.bstory.data.module

import com.example.bstory.ui.home.HomeViewModel
import com.example.bstory.ui.login.LoginViewModel
import com.example.bstory.ui.map.MapViewModel
import com.example.bstory.ui.register.RegisterViewModel
import com.example.bstory.ui.settings.SettingsViewModel
import com.example.bstory.ui.story.add.AddStoryViewModel
import com.example.bstory.ui.story.detail.DetailStoryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { LoginViewModel(get()) }
    viewModel { RegisterViewModel(get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { AddStoryViewModel(get()) }
    viewModel { DetailStoryViewModel(get()) }
    viewModel { SettingsViewModel(get()) }
    viewModel { MapViewModel(get()) }
}