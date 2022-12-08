package com.example.miapizza.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.miapizza.data.database.entities.PizzaEntity
import javax.inject.Inject

@Dao
interface PizzaDao {

    @Query("SELECT * FROM pizza_table")
    suspend fun getAllPizzas() : MutableList<PizzaEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(pizzas: MutableList<PizzaEntity>)

    @Query("SELECT * FROM pizza_table WHERE title = :title")
    suspend fun getPizza(title: String): PizzaEntity

    @Query("DELETE FROM pizza_table")
    suspend fun deleteAllPizzas()

}