package com.example.miapizza.data.model

import com.example.miapizza.R
import kotlinx.coroutines.delay
import javax.inject.Inject

class PizzaApi @Inject constructor(){

    suspend fun getPizzas(): MutableList<PizzaModel> {
        delay(3000)
        return mutableListOf(
            PizzaModel(
                id = 1,
                title = "Margarita",
                image = R.drawable.margarita,
                ingredients = mutableListOf("Tomate","Queso mozzarella", "Albahaca"),
                description = "Tomate, queso mozzarella y albahaca fresca",
                price = 300,
            ),
            PizzaModel(
                id = 2,
                title = "Siciliana",
                image = R.drawable.siciliana,
                ingredients = mutableListOf("Tomate", "Cebolla", "Queso sardo", "Anchoas", "Oregano"),
                description = "Tomate, cebolla, queso sardo, queso caciocavallo, anchoas y oregano.",
                price = 350,
            ),
            PizzaModel(
                id = 3,
                title = "Pizza con Jamon",
                image = R.drawable.jamon,
                ingredients = mutableListOf("Jamon"),
                description = "Mozzarella con Jamon.",
                price = 200,
            ),
            PizzaModel(
                id = 3,
                title = "Pizza con Panceta",
                image = R.drawable.bacon,
                ingredients = mutableListOf("Panceta"),
                description = "Mozzarella y panceta",
                price = 300,
            ),
            PizzaModel(
                id = 4,
                title = "Pizza con Aceituna",
                image = R.drawable.aceituna,
                ingredients = mutableListOf("Aceituna"),
                description = "Mozzarella y aceituna",
                price = 350,
            ),
            PizzaModel(
                id = 5,
                title = "Fugazza",
                image = R.drawable.fugazza,
                ingredients = mutableListOf("Cebolla", "Queso parmesano"),
                description = "Cebolla y queso parmesano.",
                price = 200,
            ),
            PizzaModel(
                id = 6,
                title = "Pizza con panceta",
                image = R.drawable.bacon,
                ingredients = mutableListOf("Panceta"),
                description = "Mozzarela y Panceta",
                price = 350,
            ),
            PizzaModel(
                id = 7,
                title = "Pizza con piña",
                image = R.drawable.anana,
                ingredients = mutableListOf("Jamon","Piña"),
                description = "Mozzarella, jamon y piña",
                price = 350,
            ),
            PizzaModel(
                id = 8,
                title = "Pizza con panceta y atun",
                image = R.drawable.bacon_atun,
                ingredients = mutableListOf("Panceta", "Atun"),
                description = "Mozzarella con panceta y atun",
                price = 350,
            ),
            PizzaModel(
                id = 9,
                title = "Pizza con piña",
                image = R.drawable.anana,
                ingredients = mutableListOf("Jamon","Piña"),
                description = "Mozzarella, jamon y piña",
                price = 350,
            ),
        )
    }
}