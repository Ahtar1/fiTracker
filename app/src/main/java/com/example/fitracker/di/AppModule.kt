package com.example.fitracker.di

import android.app.Application
import androidx.room.Room
import com.example.fitracker.data.data_source.ExerciseDatabase
import com.example.fitracker.data.repository.ExerciseRepositoryImpl
import com.example.fitracker.domain.repository.ExerciseRepository
import com.example.fitracker.domain.use_case.DeleteExercise
import com.example.fitracker.domain.use_case.ExerciseUseCases
import com.example.fitracker.domain.use_case.GetExercises
import com.example.fitracker.domain.use_case.InsertExercise
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideExerciseDatabase(app:Application): ExerciseDatabase {
        return Room.databaseBuilder(
            app,
            ExerciseDatabase::class.java,
            ExerciseDatabase.DATABASE_NAME
        ).build(
        )
    }

    @Provides
    @Singleton
    fun provideExerciseRepository(db: ExerciseDatabase): ExerciseRepository {
        return ExerciseRepositoryImpl(db.exerciseDao)
    }

    @Provides
    @Singleton
    fun provideExerciseUseCases(repository: ExerciseRepository): ExerciseUseCases {
        return ExerciseUseCases(
            getExercises = GetExercises(repository),
            insertExercise = InsertExercise(repository),
            deleteExercise = DeleteExercise(repository),
        )
    }

}