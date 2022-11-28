package com.example.miapizza.domain

import com.example.miapizza.data.database.entities.toDatabase
import com.example.miapizza.data.repo.PizzaRepository
import com.example.miapizza.domain.model.Pizza
import javax.inject.Inject


class GetPizzasUseCase @Inject constructor(private val repository: PizzaRepository) {

    suspend operator fun invoke(): MutableList<Pizza>{
        val pizzas = repository.getPizzas()

        return if(pizzas.isNotEmpty()){
            repository.clearPizzas()
            repository.insertPizzas(pizzas.map { it.toDatabase() }.toMutableList())
            pizzas
        }else{
            repository.getPizzasFromDatabase()
        }
    }

}