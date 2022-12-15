package com.example.miapizza.domain

import com.example.miapizza.data.model.CartItem
import javax.inject.Inject

class DeletePizzaCartUseCase @Inject constructor() {

    operator fun invoke(listCart: MutableList<CartItem>, cartItem: CartItem) : MutableList<CartItem>{
        listCart.remove(cartItem)
        return listCart
    }
}