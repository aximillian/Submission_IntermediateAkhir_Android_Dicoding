package com.example.bstory.data.response

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.bstory.data.keys.RemoteKeysDao
import com.example.bstory.data.keys.RemoteKeys
import com.example.bstory.domain.story.Story
import com.example.bstory.domain.story.StoryDao

@Database(entities = [Story::class, RemoteKeys::class], version = 2, exportSchema = false)
abstract class StoryDatabase : RoomDatabase(){

        abstract fun storyDao(): StoryDao

        abstract fun remoteKeysDao(): RemoteKeysDao
}