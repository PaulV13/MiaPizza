package com.example.miapizza.ui.view

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.miapizza.R
import com.example.miapizza.databinding.FragmentCartBinding
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
    ): View {
        _binding = FragmentCartBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewmodel.updateSubtotalPriceCart().toString()
        viewmodel.updateTotalPrice().toString()

        lifecycleScope.launchWhenStarted{
            viewmodel.state.collect{ state ->
                adapter = CartItemAdapter(
                    cartItemList = state.listCart,
                    onClickAdd = { cartItem, position ->
                        viewmodel.addCartItemQuantity(cartItem)
                        viewmodel.updatePriceCartItem(cartItem)
                        viewmodel.updateSubtotalPriceCart().toString()
                        viewmodel.updateTotalPrice().toString()
                        adapter.notifyItemChanged(position)
                    },
                    onClickMinus = { cartItem, position ->
                        if(cartItem.quantity > 1){
                            viewmodel.minusCartItemQuantity(cartItem)
                            viewmodel.updatePriceCartItem(cartItem)
                            adapter.notifyItemChanged(position)
                        }else{
                            viewmodel.deleteItemCart(cartItem)
                            adapter.notifyItemRemoved(position)
                            if(state.listCart.isEmpty()) Navigation.findNavController(binding.cardView).popBackStack()
                        }
                        viewmodel.updateSubtotalPriceCart().toString()
                        viewmodel.updateTotalPrice().toString()
                    }
                )

                binding.subtotalPrice.text = state.subTotalPriceCart.toString()
                binding.extraPrice.text = state.extraPriceCart.toString()
                binding.totalPrice.text = state.totalPriceCart.toString()
                binding.credit.text = state.credits.toString()

                binding.rvList.adapter = adapter
            }
        }

        binding.rvList.layoutManager = LinearLayoutManager(context)

        binding.totalPrice.setOnClickListener { viewmodel.addCredits() }

        binding.imageBack.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }

        binding.btnPagar.setOnClickListener{
            if(viewmodel.state.value.credits < viewmodel.state.value.totalPriceCart){
                val builder = AlertDialog.Builder(context)
                builder.setTitle(resources.getString(R.string.titleDialogCancel))
                    .setMessage(resources.getString(R.string.messageDialogCancel))
                    .setPositiveButton("OK") { dialog, _ ->
                        dialog.cancel()
                    }
                val dialog = builder.create()
                dialog.show()
            }else {
                val builder = AlertDialog.Builder(context)
                builder.setTitle(resources.getString(R.string.titleDialogOk))
                    .setMessage(resources.getString(R.string.messageDialogOk))
                    .setPositiveButton("OK") { _, _ ->
                        Navigation.findNavController(it).navigate(R.id.action_cartFragment_to_pizzaList)
                    }
                val dialog = builder.create()
                dialog.show()
                viewmodel.resetCart()
                viewmodel.discountTotalPrice()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}