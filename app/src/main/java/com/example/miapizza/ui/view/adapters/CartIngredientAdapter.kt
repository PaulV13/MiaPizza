package com.example.miapizza.ui.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.miapizza.R


class CartIngredientAdapter(private val ingredients: MutableList<String>) : RecyclerView.Adapter<CartIngredientViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartIngredientViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CartIngredientViewHolder(layoutInflater.inflate(R.layout.ingredient_item,parent,false))
    }

    override fun onBindViewHolder(holder: CartIngredientViewHolder, position: Int) {
        val item = ingredients[position]
        holder.render(item)
    }

    override fun getItemCount(): Int = ingredients.size
}