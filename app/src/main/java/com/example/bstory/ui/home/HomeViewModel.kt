package com.example.bstory.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.bstory.domain.story.Story
import com.example.bstory.domain.story.StoryRepository


class HomeViewModel(private val storyRepository: StoryRepository) : ViewModel() {
    private val _storiesResult = MutableLiveData<PagingData<Story>>()
    val storyResult: LiveData<PagingData<Story>> = _storiesResult

    fun getAllStories(size: Int = 5) {
        storyRepository.getAllStories(size).cachedIn(viewModelScope).observeForever {
            _storiesResult.value = it
        }
    }
}