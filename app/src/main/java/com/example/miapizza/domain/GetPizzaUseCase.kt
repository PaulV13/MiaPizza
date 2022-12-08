package com.example.miapizza.domain

import com.example.miapizza.data.repo.PizzaRepository
import com.example.miapizza.domain.model.Pizza
import javax.inject.Inject

class GetPizzaUseCase @Inject constructor(private val repository: PizzaRepository) {

    suspend operator fun invoke(title: String): Pizza{
        return repository.getPizza(title)
    }

}