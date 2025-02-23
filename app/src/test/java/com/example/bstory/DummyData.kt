package com.example.bstory

import com.example.bstory.domain.story.Story

object DummyData {

    fun generateNewStory(): List<Story> {
        val items: MutableList<Story> = arrayListOf()
        for (i in 0..100) {
            val story = Story(
                id = i.toString(),
                name = "Story $i",
                description = "Description $i",
                photoUrl = "https://picsum.photos/200/300?random=$i",
                createdAt = "2021-01-01T00:00:00Z",
                lat = (-7.78),
                lon = 114.04
            )
            items.add(story)
        }
        return items
    }

}