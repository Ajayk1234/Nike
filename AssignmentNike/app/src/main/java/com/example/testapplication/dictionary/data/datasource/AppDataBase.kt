package com.example.testapplication.dictionary.data.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.testapplication.dictionary.model.DictionaryEntity

@Database(entities = [DictionaryEntity::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun dictionaryDAO(): DictionaryDAO
}