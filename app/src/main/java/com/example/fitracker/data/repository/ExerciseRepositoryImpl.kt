package com.example.fitracker.data.repository

import com.example.fitracker.data.data_source.ExerciseDao
import com.example.fitracker.domain.model.Exercise
import com.example.fitracker.domain.repository.ExerciseRepository
import kotlinx.coroutines.flow.Flow

class ExerciseRepositoryImpl(
    private val dao: ExerciseDao
): ExerciseRepository {

    override suspend fun getExercises(day: Int): Flow<List<Exercise>> {
        return dao.getExercises(day)
    }

    override suspend fun insertExercise(exercise: Exercise) {
        dao.insertExercise(exercise)
    }

    override suspend fun deleteExercise(exercise: Exercise) {
        dao.deleteExercise(exercise)
    }

}
