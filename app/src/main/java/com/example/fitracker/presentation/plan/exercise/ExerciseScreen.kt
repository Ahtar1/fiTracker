package com.example.fitracker.presentation.plan.exercise

import android.os.Build
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.fitracker.domain.model.Exercise
import com.example.fitracker.presentation.plan.GifImage
import com.example.fitracker.presentation.plan.readCsv
import java.time.LocalDate
import java.time.LocalDateTime

@Composable
fun ExerciseScreen(
    navController: NavHostController,
    exerciseId: Int,
    day: Int
) {
        val viewModel: ExerciseViewModel = hiltViewModel()

        val daysOfWeek = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
        val exercises = readCsv()

        val exercise: Exercise? = exercises.find { exercise ->
            exercise.id == exerciseId
        }

        Column {
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp, bottom = 16.dp, start = 16.dp, end = 16.dp)
                    .weight(9f),
                elevation = 8.dp
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    GifImage(
                        gifUrl = exercise?.gifUrl ?: "",
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(5f)
                    )

                    Text(
                        modifier = Modifier
                            .wrapContentSize()
                            .weight(1f),
                        text = exercise?.name ?: "",
                        style = MaterialTheme.typography.h5,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        modifier = Modifier
                            .wrapContentSize()
                            .weight(1f),
                        text = exercise?.bodyPart ?: "",
                        style = MaterialTheme.typography.h5,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        modifier = Modifier
                            .wrapContentSize()
                            .weight(1f),
                        text = exercise?.equipment ?: "",
                        style = MaterialTheme.typography.h5,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(16.dp),
                onClick = { viewModel.addExercise(exercise!!,day) ;navController.navigate("plan") }
            ) {
                Text(text = "Add to ${daysOfWeek[day]}")
            }
        }

}

