package com.example.bstory.domain.story

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface StoryService {

    // Get all stories
    @GET("stories")
    suspend fun getAllStories(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20
    ): StoryResponse

    // Get all stories with location
    @GET("stories")
    suspend fun getAllStoriesWithLocation(
        @Query("location") location: Int = 1
    ): StoryResponse

    // Add new story
    @Multipart
    @POST("stories")
    suspend fun addNewStory(
        @Part photo: MultipartBody.Part,
        @Part("description") description: RequestBody,
        @Part("lat") lat: Float,
        @Part("lon") lon: Float
    ): AddStoryResponse

    // Get detail story
    @GET("stories/{id}")
    suspend fun getDetailStory(
        @Path("id") id: String
    ): DetailStoryResponse
}