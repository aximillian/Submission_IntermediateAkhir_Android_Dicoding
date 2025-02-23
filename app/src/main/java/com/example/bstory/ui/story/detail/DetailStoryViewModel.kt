package com.example.bstory.ui.story.detail

import androidx.lifecycle.ViewModel
import com.example.bstory.domain.story.StoryRepository

class DetailStoryViewModel(private val storyRepository: StoryRepository) : ViewModel() {
    fun detailStory(id: String) = storyRepository.detailStory(id)
}