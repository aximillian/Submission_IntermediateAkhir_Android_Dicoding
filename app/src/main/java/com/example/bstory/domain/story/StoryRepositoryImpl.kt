package com.example.bstory.domain.story

import android.net.Uri
import androidx.core.net.toFile
import androidx.lifecycle.LiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.bstory.config.ext.processFileImage
import com.example.bstory.data.response.ApiResponse
import com.example.bstory.data.response.StoryDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class StoryRepositoryImpl(private val storyService: StoryService, private val database: StoryDatabase) : StoryRepository {
    override fun getAllStories(size: Int): LiveData<PagingData<Story>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = size
            ),
            remoteMediator = StoryRemoteMediator(database, storyService),
            pagingSourceFactory = {
                database.storyDao().getAllStories()
            }
        ).liveData
    }

    override fun getAllStoriesWithLocation(): Flow<ApiResponse<StoryResponse>> = flow {
        try {
            emit(ApiResponse.Loading)
            val response = storyService.getAllStoriesWithLocation()
            if (response.error) {
                emit(ApiResponse.Error(response.message))
            } else {
                emit(ApiResponse.Success(response))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(ApiResponse.Error(e.message.toString()))
        }
    }

    override fun addStory(imageUri: Uri, description: String, lat: Double, lon: Double): Flow<ApiResponse<AddStoryResponse>> =
        flow {
            try {
                emit(ApiResponse.Loading)
                val photo = imageUri.toFile().processFileImage()
                val requestImageFile = photo.asRequestBody("image/*".toMediaType())
                val descriptionBody = description.toRequestBody("text/plain".toMediaType())
                val multipartBody =
                    MultipartBody.Part.createFormData("photo", photo.name, requestImageFile)

                val response = storyService.addNewStory(multipartBody, descriptionBody, lat.toFloat(), lon.toFloat())

                if (response.error) {
                    emit(ApiResponse.Error(response.message))
                } else {
                    emit(ApiResponse.Success(response))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                emit(ApiResponse.Error(e.message.toString()))
            }
        }

    override fun detailStory(id: String): LiveData<Story> {
        return database.storyDao().getDetailStory(id)
    }
}