package com.example.leboncoinchallenge.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.leboncoinchallenge.data.local.album.AlbumDao
import com.example.leboncoinchallenge.data.local.album.AlbumEntity

@Database(entities = [AlbumEntity::class], version = 1, exportSchema = false)
abstract class LebonCoinDatabase : RoomDatabase() {
    abstract fun albumDao() : AlbumDao
}