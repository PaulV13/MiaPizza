package com.example.miapizza.domain

import com.example.miapizza.data.model.CartItem
import javax.inject.Inject

class AddPizzaCartUseCase @Inject constructor() {

    operator fun invoke(listCart: MutableList<CartItem>, cartItem: CartItem) : MutableList<CartItem>{
        listCart.add(cartItem)
        return listCart
    }
}