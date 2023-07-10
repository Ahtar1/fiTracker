package com.example.fitracker.data.data_source

import androidx.room.*
import com.example.fitracker.domain.model.Exercise
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {

    @Query("SELECT * FROM exercise_table WHERE day = :day")
    fun getExercises(day: Int): Flow<List<Exercise>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercise(exercise: Exercise)

    @Delete
    suspend fun deleteExercise(exercise: Exercise)

}