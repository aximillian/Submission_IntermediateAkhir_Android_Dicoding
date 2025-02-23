package com.example.bstory.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bstory.data.response.ApiResponse
import com.example.bstory.domain.story.StoryRepository
import com.example.bstory.domain.story.StoryResponse
import kotlinx.coroutines.launch

class MapViewModel(private val storyRepository: StoryRepository) : ViewModel() {

    private val _storiesResult = MutableLiveData<ApiResponse<StoryResponse>>()
    val storyResult: LiveData<ApiResponse<StoryResponse>> by lazy { _storiesResult }

    fun getAllStoriesWithLocation() {
        viewModelScope.launch {
            storyRepository.getAllStoriesWithLocation().collect {
                _storiesResult.value = it
            }
        }
    }

}