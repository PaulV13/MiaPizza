package com.example.miapizza.domain.model

import com.example.miapizza.data.database.entities.PizzaEntity
import com.example.miapizza.data.model.PizzaModel

data class Pizza (
    val id: Int,
    val image: Int,
    val title: String,
    val description: String,
    val ingredients: MutableList<String> = arrayListOf(),
    val price: Int)


fun PizzaModel.toDomain() = Pizza(id,image, title, description,ingredients, price)
fun PizzaEntity.toDomain() = Pizza(id,image, title, description,ingredients, price)