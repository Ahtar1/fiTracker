package com.example.fitracker.presentation.plan

import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.ImageLoader
import coil.compose.SubcomposeAsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.example.fitracker.domain.model.Exercise
import java.io.BufferedReader

@Composable
fun ResultsScreen(
    navController: NavHostController,
    day: Int,
    bodyPart: String,
    exerciseName: String,
    equipment: String
) {

    val exercises = readCsv()

    println("day: $day" + "bodyPart: $bodyPart" + "exerciseName: $exerciseName" + "equipment: $equipment")

    val filteredExercises = exercises.filter { exercise ->
        (exercise.bodyPart == bodyPart || bodyPart == "any") && (exercise.name.contains(exerciseName) || exerciseName == "") && (exercise.equipment == equipment || equipment == "any")
    }

    if (filteredExercises.isEmpty()) {

        Text(text = "No exercises found")

    } else {
        LazyColumn {
            items(filteredExercises.size) { index ->
                Card(
                    modifier = Modifier.fillMaxWidth()
                        .clickable { navController.navigate("exercise/$day/${filteredExercises[index].id}") },
                    elevation = 8.dp
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .weight(3f)
                        ) {
                            Text(text = filteredExercises[index].name, maxLines = 1, overflow = TextOverflow.Ellipsis)
                            Text(text = filteredExercises[index].bodyPart, maxLines = 1, overflow = TextOverflow.Ellipsis)
                            Text(text = filteredExercises[index].equipment, maxLines = 1, overflow = TextOverflow.Ellipsis)
                        }
                        GifImage(gifUrl = filteredExercises[index].gifUrl, modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }


}

@Composable
fun readCsv(): List<Exercise> {
    val reader = BufferedReader(LocalContext.current.assets.open("fitness_exercises.csv").reader())
    val lines = reader.readLines().drop(1)
    return lines.map {
        val tokens = it.split(",")
        Exercise(
            bodyPart = tokens[0],
            equipment = tokens[1],
            gifUrl = tokens[2],
            id = tokens[3].toInt(),
            name = tokens[4],
            target = tokens[5],
            day = 0
        )
    }
}

@Composable
fun GifImage(
    modifier: Modifier = Modifier,
    gifUrl: String,
) {

    val progressState = remember { mutableStateOf(true) }
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()
    Box(modifier= Modifier.size(200.dp)){
        Image(
            painter = rememberAsyncImagePainter(
                ImageRequest.Builder(context).data(data = gifUrl)
                    .apply(block = {
                        size(Size.ORIGINAL)
                    }).build(), imageLoader = imageLoader
            ),
            contentDescription = null,
            modifier = modifier.fillMaxWidth(),
        )
    }


}
