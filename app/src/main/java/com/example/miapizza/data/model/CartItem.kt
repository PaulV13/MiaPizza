package com.example.miapizza.data.model

import com.example.miapizza.domain.model.Pizza

data class CartItem(
    val pizza: Pizza = Pizza(),
    var quantity: Int = 1,
    var price: Int = 0)
