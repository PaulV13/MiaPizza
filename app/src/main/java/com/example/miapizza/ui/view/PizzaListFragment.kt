package com.example.miapizza.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.miapizza.R
import com.example.miapizza.databinding.FragmentPizzaListBinding
import com.example.miapizza.ui.view.adapters.PizzaAdapter
import com.example.miapizza.ui.viewmodel.PizzaViewModel
import com.example.miapizza.domain.model.Pizza
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class PizzaListFragment @Inject constructor() : Fragment(R.layout.fragment_pizza_list) {
    private var _binding: FragmentPizzaListBinding? = null
    private val binding get() = _binding!!

    private val viewmodel: PizzaViewModel by activityViewModels()

    private lateinit var adapter: PizzaAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPizzaListBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pizzaList = viewmodel.listPizzas.value

        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        lifecycleScope.launch {
            viewmodel.isLoading.collect {
                binding.progress.isVisible = it
            }
        }

        lifecycleScope.launch {
            viewmodel.listPizzas.collect{ pizzas ->
                adapter = PizzaAdapter(pizzas) { onItemSelected(it) }
                binding.recyclerView.adapter = adapter
            }
        }

        binding.buttonOrange.setOnClickListener {
            viewmodel.updateSubTotalPrice()
            Navigation.findNavController(binding.searchView).navigate(R.id.action_pizzaList_to_cartFragment)
        }

        if(viewmodel.listCartItem.value.size > 0){
            binding.buttonOrange.visibility = View.VISIBLE
        }else{
            binding.buttonOrange.visibility = View.GONE
        }

        binding.chip4.text = viewmodel.sizeCartItem().toString()
        binding.buttonPrice.text = viewmodel.priceCartItem().toString()

        binding.searchView.addTextChangedListener { pizzaFilter ->
            val pizzasFiltered = pizzaList.filter { pizza -> pizza.title!!.lowercase().contains(pizzaFilter.toString().lowercase()) }
            adapter.updatePizzas(pizzasFiltered)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onItemSelected(pizza : Pizza){
        viewmodel.selectedPizza(pizza)
        viewmodel.resetQuantity()
        viewmodel.setCurrentPrice(pizza)
        Navigation.findNavController(binding.recyclerView).navigate(R.id.action_pizzaList_to_pizzaDetail)
    }
}