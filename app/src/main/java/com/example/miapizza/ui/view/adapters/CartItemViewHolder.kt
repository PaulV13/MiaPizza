package com.example.miapizza.ui.view.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.miapizza.R
import com.example.miapizza.databinding.CartItemBinding
import com.example.miapizza.data.model.CartItem

class CartItemViewHolder(view: View) : RecyclerView.ViewHolder(view){

    private val binding = CartItemBinding.bind(view)

    fun render(
        cartItem: CartItem,
        onClickMinus: (CartItem, Int) -> Unit,
        onClickAdd: (CartItem) -> Unit
    ){
        binding.pizzaTitle.text = cartItem.pizza.title
        binding.pizzaPrice.text = cartItem.pizza.price.toString()
        binding.textQuantity.text = cartItem.quantity.toString()

        Glide.with(binding.pizzaImage.context).load(cartItem.pizza.image).into(binding.pizzaImage)

        if(cartItem.quantity == 1){
            binding.symbolMinus.setImageResource(R.drawable.ic_baseline_delete_forever_24)
        }else{
            binding.symbolMinus.setImageResource(R.drawable.ic_delete_min_minus_icon)
        }

        binding.symbolMinus.setOnClickListener { onClickMinus(cartItem, adapterPosition) }
        binding.symbolAdd.setOnClickListener { onClickAdd(cartItem) }
    }
}