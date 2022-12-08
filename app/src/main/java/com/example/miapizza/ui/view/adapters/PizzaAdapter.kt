package com.example.miapizza.ui.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.miapizza.R
import com.example.miapizza.domain.model.Pizza

class PizzaAdapter(private var pizzaList: List<Pizza>, private var onClick: (Pizza) -> Unit): RecyclerView.Adapter<PizzaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PizzaViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PizzaViewHolder(layoutInflater.inflate(R.layout.pizza_item,parent,false))
    }

    override fun onBindViewHolder(viewHolder: PizzaViewHolder, position: Int) {
        val item = pizzaList[position]
        viewHolder.render(item,onClick)
    }

    override fun getItemCount(): Int = pizzaList.size
}