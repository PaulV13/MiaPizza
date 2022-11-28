package com.example.miapizza.data.model

data class Cart(val cartList: MutableList<CartItem>, val subtotalPrice: Int, val totalPrice: Int)