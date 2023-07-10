package com.example.fitracker.presentation.plan

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.Navigator
import com.example.fitracker.ui.theme.FiTrackerTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(navController: NavHostController) {

    val viewModel: MainScreenViewModel = hiltViewModel()
    val daysList =
        ("Pazartesi,Salı,Çarşamba,Perşembe,Cuma,Cumartesi,Pazar").split(",")
    val pageState = rememberPagerState(0)
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("search" + "/${pageState.currentPage}") },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                    tint = Color.White
                )
            }
        },
    ) {

        Column(Modifier.padding(it)) {

            LazyRow(
                modifier = Modifier.wrapContentSize()
                    .background(color = MaterialTheme.colors.secondary),
                verticalAlignment = Alignment.Top
            ) {

                items(daysList.size) { day ->

                    Box(
                        modifier = Modifier
                            .padding(3.dp)
                            .wrapContentSize()
                            .background(color = MaterialTheme.colors.primary)
                            .clickable {
                                coroutineScope.launch(Dispatchers.IO) {
                                    viewModel.getExercises(
                                        day
                                    ); pageState.animateScrollToPage(day)
                                }
                            }
                    ) {
                        Text(
                            text = daysList[day],
                            modifier = Modifier.padding(16.dp),
                            fontSize = 24.sp
                        )
                    }

                }

            }
            LaunchedEffect(pageState) {
                snapshotFlow { pageState.currentPage }.collect { page ->
                    viewModel.getExercises(page)
                }
            }
            HorizontalPager(
                pageCount = daysList.size,
                state = pageState,
                modifier = Modifier
                    .fillMaxSize()
            ) { page ->
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = daysList[page],
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        fontSize = 24.sp
                    )
                    println(viewModel.exercisesState.collectAsState().value.size)
                    LazyColumn {
                        items(viewModel.exercisesState.value.size) { exercise ->
                            if (viewModel.exercisesState.collectAsState().value.isEmpty()) {
                                Text(text = "No exercises", modifier = Modifier.padding(16.dp))
                            }

                            Text(
                                text = viewModel.exercisesState.collectAsState().value[exercise].name,
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxSize(),
                                fontSize = 24.sp
                            )
                        }
                    }
                }
            }
        }
    }


}


@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!", modifier = Modifier.wrapContentWidth())
}
