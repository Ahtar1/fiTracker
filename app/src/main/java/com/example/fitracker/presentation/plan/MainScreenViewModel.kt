package com.example.fitracker.presentation.plan

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitracker.domain.model.Exercise
import com.example.fitracker.domain.use_case.ExerciseUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val exerciseUseCases: ExerciseUseCases
): ViewModel() {

    private val _exercisesState = MutableStateFlow(listOf<Exercise>())
    val exercisesState = _exercisesState.asStateFlow()

    init {
        getExercises(0)
    }

    fun getExercises(day: Int){
        println("day" + day)
        viewModelScope.launch(Dispatchers.IO) {
            exerciseUseCases.getExercises(day = day).collectLatest {
                _exercisesState.value = it
            }
        }

    }
}