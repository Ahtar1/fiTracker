package com.example.fitracker.domain.repository

import com.example.fitracker.domain.model.Exercise
import kotlinx.coroutines.flow.Flow

interface ExerciseRepository {

    suspend fun getExercises(day: Int): Flow<List<Exercise>>

    suspend fun insertExercise(exercise: Exercise)

    suspend fun deleteExercise(exercise: Exercise)
}