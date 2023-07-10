package com.example.fitracker.presentation.plan

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun SearchScreen(navController: NavHostController, day: Int) {

    val bodyParts = arrayOf(
        "any","waist", "upper legs", "back", "lower legs", "chest", "upper arms", "cardio"
        , "shoulders", "lower arms", "neck"
    )
    val equipments = arrayOf(
        "any","body weight","cable","leverage machine", "assisted", "medicine ball",
        "stability ball", "band", "barbell" ,"rope", "dumbbell", "ez barbell",
        "sled machine", "upper body ergometer", "kettlebell", "olympic barbell",
        "weighted", "bosu ball", "resistance band", "roller", "skierg machine",
        "hammer", "smith machine", "wheel roller", "stationary bike", "tire",
        "trap bar" ,"elliptical machine" ,"stepmill machine"
    )



    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        val selectedBodyPart = SearchExposedDropdownMenuBox(options =bodyParts, label = "Select a body part")
        val selectedEquipment = SearchExposedDropdownMenuBox(options = equipments, label = "Select an equipment")
        var text by remember { mutableStateOf("") }
        TextField(
            value = text,
            onValueChange = { text=it },
            label = { Text("Enter the exercise name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp)
        )
        Text(text = day.toString())
        println(" search screen day: $day" + "bodyPart: $selectedBodyPart" + "equipment: $selectedEquipment" + "text: $text")
        Button(
            onClick = {if (text=="") text= " "; navController.navigate("search_results/$day/$selectedBodyPart/$selectedEquipment/$text") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
        ) {
            Text(text = "Search")
        }
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchExposedDropdownMenuBox(options: Array<String>, label: String): String {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(options[0]) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp)
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                value = selectedText,
                onValueChange = {},
                readOnly = true,
                label = { Text(label) },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { item ->
                    DropdownMenuItem(
                        onClick = {
                            selectedText = item
                            expanded = false
                        }
                    ){
                        Text(text = item)
                    }
                }
            }
        }
    }
    return selectedText
}