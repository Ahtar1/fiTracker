package com.example.fitracker.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.fitracker.domain.model.Exercise

@Database(
    entities = [Exercise::class],
    version = 1,
)
abstract class ExerciseDatabase: RoomDatabase() {

        abstract val exerciseDao: ExerciseDao

        companion object{
            const val DATABASE_NAME = "exercises_database"
        }
}