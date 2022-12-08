package com.example.miapizza.ui.view

import android.animation.LayoutTransition
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.miapizza.R
import com.example.miapizza.ui.view.viewmodel.PizzaViewModel
import com.example.miapizza.data.model.CartItem
import com.example.miapizza.databinding.FragmentPizzaDetailBinding
import com.example.miapizza.domain.model.Pizza
import com.example.miapizza.ui.view.adapters.PizzaAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
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

        val pizza: Pizza = viewmodel.state.value.pizzaSelected

        Glide.with(this).load(pizza.image).into(binding.image)

        binding.title.text = pizza.title
        binding.price.text = pizza.price.toString()

        lifecycleScope.launchWhenStarted{
            viewmodel.state.collect{ state ->
                binding.chip.text = state.pizzaSelectedQuantity.toString()
                binding.quantity.text = state.pizzaSelectedQuantity.toString()
                binding.buttonPrice.text = (pizza.price * state.pizzaSelectedQuantity).toString()
            }
        }

        binding.imageBack.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }

        binding.listCheckbox.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)

        binding.symbolAdd.setOnClickListener { viewmodel.addQuantity() }
        binding.symbolMinus.setOnClickListener { viewmodel.minusQuantity() }

        binding.buttonAdd.setOnClickListener {
            viewmodel.createItemCart()
            viewmodel.addItemCart()
            Navigation.findNavController(it).popBackStack()
        }

        onCheckboxClick(pizza)
    }

    private fun onCheckboxClick(pizza: Pizza) {
        binding.checkbox1.setOnClickListener {
            if(binding.checkbox1.isChecked){
                pizza.ingredients.add("Panceta")
            }else{
                pizza.ingredients.remove("Panceta")
            }
        }
        binding.checkbox2.setOnClickListener {
            if(binding.checkbox2.isChecked){
                pizza.ingredients.add("Aceituna")
            }else{
                pizza.ingredients.remove("Aceituna")
            }
        }
        binding.checkbox3.setOnClickListener {
            if(binding.checkbox3.isChecked){
                pizza.ingredients.add("Jamon")
            }else{
                pizza.ingredients.remove("Jamon")
            }
        }
        binding.checkbox4.setOnClickListener {
            if(binding.checkbox4.isChecked){
                pizza.ingredients.add("Anana")
            }else{
                pizza.ingredients.remove("Anana")
            }
        }
        binding.checkbox5.setOnClickListener {
            if(binding.checkbox5.isChecked){
                pizza.ingredients.add("Huevo")
            }else{
                pizza.ingredients.remove("Huevo")
            }
        }
        binding.checkbox6.setOnClickListener {
            if(binding.checkbox6.isChecked){
                pizza.ingredients.add("Lechuga")
            }else{
                pizza.ingredients.remove("Lechuga")
            }
        }
        binding.checkbox7.setOnClickListener {
            if(binding.checkbox7.isChecked){
                pizza.ingredients.add("Pepino")
            }else{
                pizza.ingredients.remove("Pepino")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}