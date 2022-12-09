package com.example.miapizza.ui.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.miapizza.data.model.CartItem
import com.example.miapizza.domain.GetPizzaUseCase
import com.example.miapizza.domain.GetPizzasUseCase
import com.example.miapizza.domain.model.Pizza
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PizzaViewModel @Inject constructor(
   private val getPizzasUseCase: GetPizzasUseCase,
   private val getPizzaUseCase: GetPizzaUseCase
) : ViewModel() {


    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update { it.copy(loading = true) }
            val pizzas = getPizzasUseCase.invoke()
            _state.update { it.copy(pizzas = pizzas) }
            _state.update { it.copy(loading = false) }
        }
    }

    suspend fun getPizza(pizza: Pizza) {
        _state.update { it.copy(pizzaSelected = getPizzaUseCase.invoke(pizza.title)) }
    }

    fun addQuantity() {
        _state.update { it.copy(pizzaSelectedQuantity = it.pizzaSelectedQuantity + 1) }
    }

    fun minusQuantity() {
        _state.update {
            it.copy(pizzaSelectedQuantity = if(it.pizzaSelectedQuantity > 1) it.pizzaSelectedQuantity - 1 else 1)
        }
    }

    fun resetQuantity(){
        _state.update {
            it.copy(pizzaSelectedQuantity = 1)
        }
    }

    fun createItemCart() {
        _state.update { it.copy(itemCart = CartItem(
            pizza = _state.value.pizzaSelected,
            quantity = _state.value.pizzaSelectedQuantity,
            price = _state.value.pizzaSelected.price * _state.value.pizzaSelectedQuantity
        )) }
    }

    fun addItemCart(){
        val listCart = _state.value.listCart
        listCart.add(_state.value.itemCart)
        _state.update {
            it.copy(
                listCart = listCart
            )
        }
    }

    fun totalPriceCart(): Int {
        val listCart = _state.value.listCart
        var totalPrice = 0
        for(item in listCart){
           totalPrice += item.price
        }
        return totalPrice
    }

    fun totalQuantityCart(): Int {
        val listCart = _state.value.listCart
        var totalQuantity = 0
        for(item in listCart){
            totalQuantity += item.quantity
        }
        return totalQuantity
    }

    fun addCartItemQuantity(cartItem: CartItem) {
        cartItem.quantity += 1
    }

    fun minusCartItemQuantity(cartItem: CartItem) {
        if(cartItem.quantity > 1){
            cartItem.quantity -= 1
        }
    }

    fun deleteItemCart(cartItem: CartItem) {
        val listCart = _state.value.listCart
        listCart.remove(cartItem)
        _state.update {
            it.copy(
                listCart = listCart
            )
        }
    }

    fun updateSubtotalPriceCart(){
        val listCart = _state.value.listCart
        var subtotalPrice = 0
        for (itemCart in listCart){
            subtotalPrice += itemCart.price
        }
        _state.update { it.copy( subTotalPriceCart = subtotalPrice) }
    }

    fun updateTotalPrice() {
        val totalPrice = _state.value.extraPriceCart + _state.value.subTotalPriceCart
        _state.update { it.copy( totalPriceCart = totalPrice ) }
    }

    fun updatePriceCartItem(cartItem: CartItem) {
        cartItem.price = cartItem.quantity * cartItem.pizza.price
    }

    fun addCredits() {
        val credit = _state.value.credits + 20
        _state.update { it.copy( credits = credit) }
    }

    fun resetCart() {
        _state.update { it.copy( listCart = mutableListOf()) }
    }

    fun discountTotalPrice() {
        val credits = _state.value.credits - _state.value.totalPriceCart
        _state.update { it.copy(credits = credits) }
    }

    fun addIngredient(ingredient: String) {
        val ingredients = _state.value.pizzaSelected.ingredients
        ingredients.add(ingredient)

        val pizza = _state.value.pizzaSelected.copy(ingredients = ingredients)
        _state.update { it.copy(pizzaSelected = pizza) }
    }

    fun removeIngredient(ingredient: String) {
        val ingredients = _state.value.pizzaSelected.ingredients
        ingredients.remove(ingredient)

        val pizza = _state.value.pizzaSelected.copy(ingredients = ingredients)
        _state.update { it.copy(pizzaSelected = pizza) }
    }

    data class UiState(
        val loading: Boolean = false,
        val credits: Int = 3000,
        val pizzas: List<Pizza> = emptyList(),
        val listCart: MutableList<CartItem> = mutableListOf(),
        val pizzaSelected: Pizza = Pizza(),
        val pizzaSelectedQuantity: Int = 1,
        val itemCart: CartItem = CartItem(),
        val extraPriceCart: Int = 39,
        val totalPriceCart: Int = 0,
        val subTotalPriceCart: Int = 0,
    )
}