package com.example.miapizza.data.database.entities

import androidx.annotation.DrawableRes
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.miapizza.domain.model.Pizza

@Entity(tableName = "pizza_table")
data class PizzaEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @DrawableRes
    @ColumnInfo(name = "image") val image: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "ingredients") val ingredients: MutableList<String> = arrayListOf(),
    @ColumnInfo(name = "price") val price: Int
)


fun Pizza.toDatabase() = PizzaEntity(image = image, title = title, description = description, ingredients = ingredients, price = price)
