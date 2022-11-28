package com.example.miapizza.di

import android.content.Context
import androidx.room.Room
import com.example.miapizza.data.database.PizzaDatabase
import com.example.miapizza.data.database.dao.PizzaDao
import com.example.miapizza.data.model.PizzaApi
import com.example.miapizza.data.repo.PizzaRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val PIZZA_DATABASE_NAME = "pizza_database"

    @Provides
    @Singleton
    fun providePizzaApi(): PizzaApi = PizzaApi()

    @Provides
    @Singleton
    fun provideRepository(pizzaApi: PizzaApi, pizzaDao: PizzaDao): PizzaRepository = PizzaRepository(pizzaApi,pizzaDao)

    @Singleton
    @Provides
    fun providePizzaDao(db: PizzaDatabase) = db.getPizzaDao()

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context,PizzaDatabase::class.java,PIZZA_DATABASE_NAME).build()

}