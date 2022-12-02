package com.example.miapizza.ui.view.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.miapizza.databinding.IngredientItemBinding

class CartIngredientViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = IngredientItemBinding.bind(view)

    fun render(ingredient: String){
        binding.itemIngredient.text = ingredient
    }

}