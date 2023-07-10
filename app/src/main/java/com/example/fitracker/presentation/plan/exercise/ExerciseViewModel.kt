package com.example.fitracker.presentation.plan.exercise

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitracker.domain.model.Exercise
import com.example.fitracker.domain.use_case.ExerciseUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseViewModel @Inject constructor(
    private val exerciseUseCases: ExerciseUseCases
) : ViewModel(

) {

    fun addExercise(exercise: Exercise,day: Int) {
        viewModelScope.launch {
            val newExercise = exercise.copy(day = day)
            exerciseUseCases.insertExercise(newExercise)
        }

    }
}