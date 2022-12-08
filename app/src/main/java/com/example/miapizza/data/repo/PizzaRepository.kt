package com.example.miapizza.data.repo

import com.example.miapizza.data.database.dao.PizzaDao
import com.example.miapizza.data.database.entities.PizzaEntity
import com.example.miapizza.data.model.PizzaModel
import com.example.miapizza.data.model.PizzaApi
import com.example.miapizza.domain.model.Pizza
import com.example.miapizza.domain.model.toDomain
import javax.inject.Inject

class PizzaRepository @Inject constructor(
    private val pizzaApi: PizzaApi,
    private val pizzaDao: PizzaDao
) {

    suspend fun getPizzas(): MutableList<Pizza>{
        val response: MutableList<PizzaModel> = pizzaApi.getPizzas()
        return response.map { it.toDomain() }.toMutableList()
    }

    suspend fun getPizzasFromDatabase(): MutableList<Pizza>{
        val response: MutableList<PizzaEntity> = pizzaDao.getAllPizzas()
        return response.map { it.toDomain() }.toMutableList()
    }

    suspend fun insertPizzas(pizzas:MutableList<PizzaEntity>){
        pizzaDao.insertAll(pizzas)
    }

    suspend fun clearPizzas(){
        pizzaDao.deleteAllPizzas()
    }

    suspend fun getPizza(title: String): Pizza{
        return pizzaDao.getPizza(title).toDomain()
    }
}