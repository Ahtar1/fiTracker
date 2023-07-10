package com.example.fitracker

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.fitracker.domain.model.Exercise
import com.example.fitracker.presentation.CalendarScreen
import com.example.fitracker.presentation.plan.exercise.ExerciseScreen
import com.example.fitracker.ui.BottomNavItem
import com.example.fitracker.presentation.plan.MainScreen
import com.example.fitracker.presentation.plan.ResultsScreen
import com.example.fitracker.presentation.plan.SearchScreen
import com.example.fitracker.ui.theme.FiTrackerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FiTrackerTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()
                val bottomBarState = rememberSaveable { (mutableStateOf(true)) }
                var exercises = listOf<Exercise>()



                Scaffold(
                    bottomBar = {
                        if(bottomBarState.value){
                            BottomNavigationBar(
                                items = listOf(
                                    BottomNavItem(
                                        name = "Plan",
                                        route = "plan",
                                        icon = Icons.Default.ViewWeek
                                    ),
                                    BottomNavItem(
                                        name = "Calendar",
                                        route = "calendar",
                                        icon = Icons.Default.CalendarMonth
                                    )
                                ),
                                navController = navController,
                                onItemClick = { navController.navigate(it.route) }
                            )
                        }
                    }
                ) {
                    Column(modifier = Modifier.padding(paddingValues = it)) {
                        Navigation(navController = navController,bottomBarState = bottomBarState)
                    }

                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation(navController: NavHostController,bottomBarState: MutableState<Boolean>) {
    NavHost(navController = navController, startDestination = "plan") {
        composable("plan") {
            MainScreen(navController = navController)
            bottomBarState.value = true
        }

        composable("calendar") {
            CalendarScreen()
            bottomBarState.value = true
        }

        composable("search/{day}", arguments = listOf(navArgument("day") {
            type = NavType.IntType
            defaultValue = -1
            nullable = false
        })) {
            bottomBarState.value = false
            SearchScreen(navController = navController, day = it.arguments?.getInt("day") ?: -1)

        }

        composable("search_results/{day}/{bodyPart}/{equipment}/{exerciseName}", arguments = listOf(
            navArgument("day") {
                type = NavType.IntType
                defaultValue = -1
                nullable = false
            },
            navArgument("bodyPart") {
                type = NavType.StringType
                defaultValue = "any"
                nullable = false
            },
            navArgument("equipment") {
                type = NavType.StringType
                defaultValue = "any"
                nullable = false
            },
            navArgument("exerciseName") {
                type = NavType.StringType
                defaultValue = ""
                nullable = false
            })){
            bottomBarState.value = false
            ResultsScreen(
                navController = navController,
                day = it.arguments?.getInt("day") ?: -1,
                bodyPart = it.arguments?.getString("bodyPart") ?: "any",
                equipment = it.arguments?.getString("equipment") ?: "any",
                exerciseName = it.arguments?.getString("exerciseName") ?: ""
            )
        }

        composable("exercise/{day}/{id}", arguments = listOf(
            navArgument("day") {
                type = NavType.IntType
                defaultValue = -1
                nullable = false
            },
            navArgument("id") {
                type = NavType.IntType
                defaultValue = -1
                nullable = false
            }
        )){
            bottomBarState.value = false
            ExerciseScreen(
                navController = navController,
                exerciseId = it.arguments?.getInt("id") ?: -1,
                day = it.arguments?.getInt("day") ?: -1
            )
        }

    }
}

@Composable
fun BottomNavigationBar(
    items: List<BottomNavItem>,
    navController: NavController,
    modifier: Modifier = Modifier,
    onItemClick: (BottomNavItem) -> Unit,
    ){
        val navBackStackEntry = navController.currentBackStackEntryAsState()
        BottomNavigation(
            modifier = modifier,
            backgroundColor = MaterialTheme.colors.background,
            elevation = 5.dp,
        ){
            items.forEach { item->
                val selected = item.route == navBackStackEntry.value?.destination?.route
                BottomNavigationItem(
                    selected = selected,
                    onClick = { onItemClick(item) },
                    selectedContentColor = MaterialTheme.colors.primary,
                    unselectedContentColor = MaterialTheme.colors.onBackground,
                    icon = {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.name,
                            )
                            if (selected) {
                                Text(
                                    text = item.name,
                                    textAlign = TextAlign.Center,
                                    fontSize = 10.sp
                                )
                            }
                        }
                    }
                )
            }
        }
}


@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!", modifier = Modifier.wrapContentWidth())
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FiTrackerTheme {


    }
}