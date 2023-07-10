package com.example.fitracker.domain.model

import androidx.room.Entity

@Entity(tableName = "exercise_table", primaryKeys = ["id"])
data class Exercise(
    val bodyPart: String,
    val equipment: String,
    val gifUrl: String,
    val id: Int,
    val name: String,
    val target: String,
    val day: Int,
)
//bodyPart,equipment,gifUrl,id,name,target
