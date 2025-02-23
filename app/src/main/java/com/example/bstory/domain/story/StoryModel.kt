package com.example.bstory.domain.story

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.bstory.data.response.DetailResponse
import com.google.gson.annotations.SerializedName

// StoryResponse is a data class that contains a list of Story objects.
data class StoryResponse(
    @field:SerializedName("listStory")
    val listStory: List<Story> = emptyList(),
): DetailResponse()

class AddStoryResponse: DetailResponse()

data class DetailStoryResponse(
    @field:SerializedName("story")
    val story: Story,
): DetailResponse()


// AddStoryResponse is an empty class that extends DetailResponse.
@Entity(tableName = "stories")
data class Story(

    @PrimaryKey
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("photoUrl")
    val photoUrl: String,

    @field:SerializedName("createdAt")
    val createdAt: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("lon")
    val lon: Double = 0.0,

    @field:SerializedName("lat")
    val lat: Double = 0.0
)