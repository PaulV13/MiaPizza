package com.example.miapizza.ui.view

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
import com.example.miapizza.databinding.FragmentPizzaListBinding
import com.example.miapizza.ui.view.adapters.PizzaAdapter
import com.example.miapizza.ui.view.viewmodel.PizzaViewModel
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

        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        viewmodel.resetQuantity()

        lifecycleScope.launchWhenStarted{
            viewmodel.state.collect{ state ->
                binding.progress.visibility = if(state.loading) View.VISIBLE else View.GONE
                adapter = PizzaAdapter(state.pizzas, onClick = {
                    lifecycleScope.launch{
                        viewmodel.getPizza(it)
                        Navigation.findNavController(binding.recyclerView).navigate(R.id.action_pizzaList_to_pizzaDetail)
                    }
                })
                binding.recyclerView.adapter = adapter
                adapter.notifyDataSetChanged()

                if(state.listCart.isEmpty()) binding.bottomBar.visibility = View.GONE else View.VISIBLE

                binding.buttonPrice.text = viewmodel.totalPriceCart().toString()
                binding.chip.text = viewmodel.totalQuantityCart().toString()

                binding.buttonCart.setOnClickListener {
                    Navigation.findNavController(binding.recyclerView).navigate(R.id.action_pizzaList_to_cartFragment)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}