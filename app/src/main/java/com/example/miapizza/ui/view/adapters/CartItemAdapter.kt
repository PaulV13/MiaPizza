package com.example.miapizza.ui.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.miapizza.R
import com.example.miapizza.data.model.CartItem

class CartItemAdapter(
    private val cartItemList: MutableList<CartItem>,
    private val onClickMinus: (CartItem, Int) -> Unit,
    private val onClickAdd: (CartItem, Int) -> Unit
    ): RecyclerView.Adapter<CartItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CartItemViewHolder(layoutInflater.inflate(R.layout.cart_item,parent,false))
    }

    override fun onBindViewHolder(viewHolder: CartItemViewHolder, position: Int) {
        val item = cartItemList[position]
        viewHolder.render(item, onClickMinus, onClickAdd)
    }

    override fun getItemCount(): Int = cartItemList.size

}