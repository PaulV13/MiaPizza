package com.example.miapizza.data.model


import androidx.annotation.DrawableRes


data class PizzaModel (
    val id: Int,
    @DrawableRes val image: Int,
    val title: String,
    val description: String,
    val ingredients: MutableList<String> = mutableListOf(),
    val price: Int)