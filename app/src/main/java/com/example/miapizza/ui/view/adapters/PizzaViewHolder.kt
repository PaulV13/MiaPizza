package com.example.miapizza.ui.view.adapters


import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.miapizza.databinding.PizzaItemBinding
import com.example.miapizza.domain.model.Pizza

class PizzaViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = PizzaItemBinding.bind(view)

    fun render(pizza : Pizza, onClick: (Pizza) -> Unit){
        binding.pizzaTitle.text = pizza.title
        binding.pizzaDescription.text = pizza.description
        binding.pizzaPrice.text = pizza.price.toString()
        Glide.with(binding.pizzaImage.context).load(pizza.image).into(binding.pizzaImage)

        itemView.setOnClickListener { onClick(pizza) }
    }
}