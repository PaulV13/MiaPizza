package com.example.miapizza.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.miapizza.databinding.FragmentCartBinding
import com.example.miapizza.data.model.Cart
import com.example.miapizza.data.model.CartItem
import com.example.miapizza.ui.view.adapters.CartItemAdapter
import com.example.miapizza.ui.viewmodel.PizzaViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CartFragment @Inject constructor()  : Fragment(){

    private var _binding : FragmentCartBinding? = null
    private val binding get() = _binding!!

    private val viewmodel: PizzaViewModel by activityViewModels()

    private lateinit var adapter: CartItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCartBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenStarted {
            viewmodel.subTotalPrice.collect{ subTotalPrice ->
                binding.subtotalPrice.text = subTotalPrice.toString()
            }
        }

        binding.sendPrice.text = viewmodel.priceSend.value.toString()

        lifecycleScope.launchWhenStarted {
            viewmodel.totalPrice.collect{
                binding.totalPrice.text = viewmodel.totalPrice.value.toString()
            }
        }

        adapter = CartItemAdapter(
            cartItemList = viewmodel.listCartItem.value,
            onClickMinus = {cartItem, position -> minusQuantityItem(cartItem, position)},
            onClickAdd = {cartItem -> addQuantityItem(cartItem)}
        )

        binding.rvList.layoutManager = LinearLayoutManager(context)
        binding.rvList.adapter = adapter

        binding.imageBack.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }

        binding.btnPagar.setOnClickListener {
            val cart = Cart(
                viewmodel.listCartItem.value,
                viewmodel.subTotalPrice.value,
                viewmodel.subTotalPrice.value + viewmodel.priceSend.value
            )

            Toast.makeText(context, cart.cartList.map {
                it.pizza.title + " " + it.pizza.description
            }.toString() + "\n - Subtotal:" +
                    cart.subtotalPrice + "\n - Total price: " + cart.totalPrice,Toast.LENGTH_LONG).show()
        }
    }

    private fun addQuantityItem(cartItem: CartItem){
        viewmodel.addCartItemQuantity(cartItem)
        viewmodel.updateSubTotalPrice()
        adapter.notifyDataSetChanged()
    }

    private fun minusQuantityItem(cartItem: CartItem, position: Int) {
        viewmodel.minusCartItemQuantity(cartItem)
        viewmodel.updateSubTotalPrice()
        adapter.notifyDataSetChanged()

        if(cartItem.quantity == 0){
            deleteItem(cartItem, position)
        }
    }

    private fun deleteItem(cartItem: CartItem, position: Int){
        viewmodel.removeItemCart(cartItem)
        adapter.notifyItemRemoved(position)

        if(viewmodel.listCartItem.value.isEmpty()){
            Navigation.findNavController(binding.divider2).popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}