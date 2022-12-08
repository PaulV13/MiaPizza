package com.example.miapizza.domain.model

import com.example.miapizza.data.database.entities.PizzaEntity
import com.example.miapizza.data.model.PizzaModel

data class Pizza (
    val id: Int = 0,
    val image: Int = 0,
    val title: String = "",
    val description: String = "",
    val ingredients: MutableList<String> = arrayListOf(),
    val price: Int = 0)


fun PizzaModel.toDomain() = Pizza(id,image, title, description,ingredients, price)
fun PizzaEntity.toDomain() = Pizza(id,image, title, description,ingredients, price)