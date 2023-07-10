package com.example.fitracker.domain.use_case

import com.example.fitracker.domain.model.Exercise
import com.example.fitracker.domain.repository.ExerciseRepository
import kotlinx.coroutines.flow.Flow

class GetExercises(
    private val exerciseRepository: ExerciseRepository
) {

        suspend operator fun invoke(day: Int): Flow<List<Exercise>>{
            return exerciseRepository.getExercises(day)
        }
}