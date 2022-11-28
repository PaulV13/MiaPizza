package com.example.miapizza.data.model

import com.example.miapizza.domain.model.Pizza

data class CartItem(val pizza: Pizza, var quantity: Int, val price: Int)
