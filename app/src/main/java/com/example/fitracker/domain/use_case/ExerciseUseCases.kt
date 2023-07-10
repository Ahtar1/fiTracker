package com.example.fitracker.domain.use_case

data class ExerciseUseCases(
    val getExercises: GetExercises,
    val deleteExercise: DeleteExercise,
    val insertExercise: InsertExercise
) {
}