package com.example.miapizza.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.miapizza.data.database.dao.PizzaDao
import com.example.miapizza.data.database.entities.PizzaEntity

@Database(entities = [PizzaEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class PizzaDatabase : RoomDatabase() {

    abstract fun getPizzaDao(): PizzaDao
}