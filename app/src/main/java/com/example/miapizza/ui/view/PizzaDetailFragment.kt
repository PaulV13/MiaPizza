package com.example.miapizza.ui.view

import android.animation.LayoutTransition
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.miapizza.R
import com.example.miapizza.ui.view.viewmodel.PizzaViewModel
import com.example.miapizza.databinding.FragmentPizzaDetailBinding
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

        selectIngredients(pizza)
        onCheckboxClick(pizza)
    }

    private fun selectIngredients(pizza: Pizza) {
        for(ingredient in pizza.ingredients){
            when(ingredient){
                resources.getString(R.string.panceta) -> { binding.checkbox1.isChecked = true }
                resources.getString(R.string.aceituna) -> { binding.checkbox2.isChecked = true }
                resources.getString(R.string.jamon) -> { binding.checkbox3.isChecked = true }
                resources.getString(R.string.anana) -> { binding.checkbox4.isChecked = true }
                resources.getString(R.string.huevo) -> { binding.checkbox5.isChecked = true }
                resources.getString(R.string.lechuga) -> { binding.checkbox6.isChecked = true }
                resources.getString(R.string.pepino) -> { binding.checkbox7.isChecked = true }
                else -> {}
            }
        }
    }

    private fun onCheckboxClick(pizza: Pizza) {
        binding.checkbox1.setOnClickListener {
            if(binding.checkbox1.isChecked){
                viewmodel.addIngredient(resources.getString(R.string.panceta))
            }else{
                viewmodel.removeIngredient(resources.getString(R.string.panceta))
            }
        }
        binding.checkbox2.setOnClickListener {
            if(binding.checkbox2.isChecked){
                viewmodel.addIngredient(resources.getString(R.string.aceituna))
            }else{
                viewmodel.removeIngredient(resources.getString(R.string.aceituna))
            }
        }
        binding.checkbox3.setOnClickListener {
            if(binding.checkbox3.isChecked){
                viewmodel.addIngredient(resources.getString(R.string.jamon))
            }else{
                viewmodel.removeIngredient(resources.getString(R.string.jamon))
            }
        }
        binding.checkbox4.setOnClickListener {
            if(binding.checkbox4.isChecked){
                viewmodel.addIngredient(resources.getString(R.string.anana))
            }else{
                viewmodel.removeIngredient(resources.getString(R.string.anana))
            }
        }
        binding.checkbox5.setOnClickListener {
            if(binding.checkbox5.isChecked){
                viewmodel.addIngredient(resources.getString(R.string.huevo))
            }else{
                viewmodel.removeIngredient(resources.getString(R.string.huevo))
            }
        }
        binding.checkbox6.setOnClickListener {
            if(binding.checkbox6.isChecked){
                viewmodel.addIngredient(resources.getString(R.string.lechuga))
            }else{
                viewmodel.removeIngredient(resources.getString(R.string.lechuga))
            }
        }
        binding.checkbox7.setOnClickListener {
            if(binding.checkbox7.isChecked){
                viewmodel.addIngredient(resources.getString(R.string.pepino))
            }else{
                viewmodel.removeIngredient(resources.getString(R.string.pepino))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}