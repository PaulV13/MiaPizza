package com.example.miapizza.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.miapizza.R
import com.example.miapizza.databinding.FragmentPizzaDetailBinding
import com.example.miapizza.data.model.CartItem
import com.example.miapizza.ui.viewmodel.PizzaViewModel
import com.example.miapizza.domain.model.Pizza
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PizzaDetailFragment @Inject constructor() : Fragment(R.layout.fragment_pizza_detail) {

    private var _binding: FragmentPizzaDetailBinding? = null
    private val binding get() = _binding!!

    private val viewmodel: PizzaViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPizzaDetailBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var checkboxIsVisible = true
        var pizza: Pizza = viewmodel.pizza.value

        Glide.with(this).load(pizza.image).into(binding.image)
        binding.title.text = pizza.title
        binding.price.text = pizza.price.toString()

        binding.buttonPrice.text = pizza.price.toString()
        binding.chip4.text = pizza.id.toString()
        binding.textQuantity.text = pizza.id.toString()

        binding.imageBack.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }

        binding.iconUp.setOnClickListener {
            if(checkboxIsVisible){
                it.rotationX = 180f
            }else{
                it.rotationX = 0f
            }
            checkboxIsVisible = !checkboxIsVisible
            changeVisibleCheckbox(checkboxIsVisible)
        }

        binding.symbolMinus.setOnClickListener {
            if(viewmodel.currentQuantity.value > 1) {
                viewmodel.removeQuantity()
            }
            viewmodel.updatePrice(pizza)
        }

        binding.checkbox1.setOnClickListener {
            onCheckboxClicked(it, pizza)
        }
        binding.checkbox2.setOnClickListener {
            onCheckboxClicked(it,pizza)
        }
        binding.checkbox3.setOnClickListener {
            onCheckboxClicked(it,pizza)
        }
        binding.checkbox4.setOnClickListener {
            onCheckboxClicked(it,pizza)
        }
        binding.checkbox5.setOnClickListener {
            onCheckboxClicked(it,pizza)
        }
        binding.checkbox6.setOnClickListener {
            onCheckboxClicked(it,pizza)
        }
        binding.checkbox7.setOnClickListener {
            onCheckboxClicked(it,pizza)
        }

        binding.symbolAdd.setOnClickListener {
            viewmodel.addQuantity()
            viewmodel.updatePrice(pizza)
        }

        lifecycleScope.launchWhenStarted {
            viewmodel.currentQuantity.collect{
                binding.textQuantity.text = it.toString()
                binding.chip4.text = it.toString()
            }
        }

        lifecycleScope.launchWhenStarted {
            viewmodel.currentPrice.collect { binding.buttonPrice.text = it.toString() }
        }

        binding.buttonOrange.setOnClickListener {
            val cartItem = CartItem(pizza, viewmodel.currentQuantity.value, viewmodel.currentQuantity.value * pizza.price)
            val list = viewmodel.listCartItem.value
            list.add(cartItem)

            Navigation.findNavController(it).popBackStack()
        }
    }

    private fun changeVisibleCheckbox(checkboxIsVisible: Boolean) {
        if(checkboxIsVisible){
            binding.listCheckbox.visibility = View.VISIBLE
        }else{
            binding.listCheckbox.visibility = View.GONE
        }
    }

    private fun onCheckboxClicked(view: View, pizza: Pizza) {
        if (view is CheckBox) {
            val checked: Boolean = view.isChecked

            when (view.id) {
                R.id.checkbox1 -> {
                    if (checked) {
                        pizza.ingredients.add(binding.checkbox1.text.toString())
                    } else {
                        pizza.ingredients.remove(binding.checkbox1.text.toString())
                    }
                }
                R.id.checkbox2 -> {
                    if (checked) {
                        pizza.ingredients.add(binding.checkbox2.text.toString())
                    } else {
                        pizza.ingredients.remove(binding.checkbox2.text.toString())
                    }
                }
                R.id.checkbox3 -> {
                    if (checked) {
                        pizza.ingredients.add(binding.checkbox3.text.toString())
                    } else {
                        pizza.ingredients.remove(binding.checkbox3.text.toString())
                    }
                }
                R.id.checkbox4 -> {
                    if (checked) {
                        pizza.ingredients.add(binding.checkbox4.text.toString())
                    } else {
                        pizza.ingredients.remove(binding.checkbox4.text.toString())
                    }
                }
                R.id.checkbox5 -> {
                    if (checked) {
                        pizza.ingredients.add(binding.checkbox5.text.toString())
                    } else {
                        pizza.ingredients.remove(binding.checkbox5.text.toString())
                    }
                }
                R.id.checkbox6 -> {
                    if (checked) {
                        pizza.ingredients.add(binding.checkbox6.text.toString())
                    } else {
                        pizza.ingredients.remove(binding.checkbox6.text.toString())
                    }
                }
                R.id.checkbox7 -> {
                    if (checked) {
                        pizza.ingredients.add(binding.checkbox7.text.toString())
                    } else {
                        pizza.ingredients.remove(binding.checkbox7.text.toString())
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}