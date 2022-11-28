package com.example.miapizza.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.miapizza.R
import com.example.miapizza.data.model.CartItem
import com.example.miapizza.domain.GetPizzasUseCase
import com.example.miapizza.domain.model.Pizza
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PizzaViewModel @Inject constructor(
   private val getPizzasUseCase: GetPizzasUseCase
) : ViewModel() {

    private val _currentQuantity = MutableStateFlow(1)
    val currentQuantity: StateFlow<Int> get() = _currentQuantity

    private val _currentPrice = MutableStateFlow(0)
    val currentPrice: StateFlow<Int> get() = _currentPrice

    private val _priceSend = MutableStateFlow(39)
    val priceSend: StateFlow<Int> get() = _priceSend

    private val _subTotalPrice = MutableStateFlow(0)
    val subTotalPrice: StateFlow<Int> get() = _subTotalPrice

    private val _totalPrice = MutableStateFlow(0)
    val totalPrice: StateFlow<Int> get() = _totalPrice

    private var _listCarItem = MutableStateFlow<MutableList<CartItem>>(mutableListOf())
    val listCartItem: StateFlow<MutableList<CartItem>> get() = _listCarItem

    private var _listPizzas = MutableStateFlow<MutableList<Pizza>>(mutableListOf())
    val listPizzas: StateFlow<MutableList<Pizza>> get() = _listPizzas

    private val _isLoading = MutableStateFlow(true)
    val isLoading : StateFlow<Boolean> get() = _isLoading

    private var _pizza = MutableStateFlow(
        Pizza(
        id = 0,
        title = "",
        image = R.drawable.margarita,
        ingredients = mutableListOf(),
        description = "",
        price = 0,
    )
    )
    val pizza : StateFlow<Pizza> get() = _pizza

    init{
        viewModelScope.launch {
            _isLoading.value = true

            val result = getPizzasUseCase()

            if(!result.isNullOrEmpty()){
                _listPizzas.value = result
                _isLoading.value = false
            }
        }
    }

    fun addQuantity() {
        _currentQuantity.value = _currentQuantity.value.plus(1)
    }

    fun removeQuantity() {
        _currentQuantity.value = _currentQuantity.value.minus(1)
    }

    fun sizeCartItem(): Int{
        var size = 0

        for (cartItem in listCartItem.value){
            size += cartItem.quantity
        }

        return size
    }

    fun priceCartItem(): Int{
        var price = 0

        for (cartItem in listCartItem.value){
            price += cartItem.price
        }

        return price
    }

    fun updatePrice(pizza: Pizza) {
        _currentPrice.value = pizza.price * _currentQuantity.value
    }

    fun updateSubTotalPrice() {
        var subTotalPrice = 0
        for (cartItem in listCartItem.value){
            subTotalPrice += cartItem.pizza.price * cartItem.quantity
        }
        _subTotalPrice.value = subTotalPrice
        updateTotalPrice()
    }

    fun updateTotalPrice(){
        _totalPrice.value = _subTotalPrice.value + _priceSend.value
    }

    fun setCurrentPrice(pizza: Pizza) {
        _currentPrice.value = pizza.price
    }

    fun resetQuantity() {
        _currentQuantity.value = 1
    }

    fun removeItemCart(carItem: CartItem) {
        _listCarItem.value.remove(carItem)
    }

    fun addCartItemQuantity(item: CartItem) {
        item.quantity += 1
    }

    fun minusCartItemQuantity(item: CartItem) {
        item.quantity -= 1
    }

    fun selectedPizza(pizza: Pizza) {
        _pizza.value = pizza
    }
}