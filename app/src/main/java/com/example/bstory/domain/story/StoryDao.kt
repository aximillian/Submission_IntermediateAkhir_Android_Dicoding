package com.example.bstory.domain.story

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStory(story: List<Story>)

    @Query("SELECT * FROM stories ORDER BY createdAt DESC")
    fun getAllStories(): PagingSource<Int, Story>

    @Query("SELECT * FROM stories WHERE id = :id")
    fun getDetailStory(id: String): LiveData<Story>

    @Query("DELETE FROM stories")
    suspend fun deleteAll()
}