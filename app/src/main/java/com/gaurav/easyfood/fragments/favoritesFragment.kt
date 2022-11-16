package com.gaurav.easyfood.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.gaurav.easyfood.R
import com.gaurav.easyfood.activities.MainActivity
import com.gaurav.easyfood.adapter.FavoriteMealAdapter
import com.gaurav.easyfood.databinding.FragmentFavoritesBinding
import com.gaurav.easyfood.databinding.FragmentHomeBinding
import com.gaurav.easyfood.viewModels.HomeViewModel
import com.google.android.material.snackbar.Snackbar

class favoritesFragment : Fragment() {

    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var favoriteMealAdapter: FavoriteMealAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel =(activity as MainActivity).viewModel

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritesBinding.inflate(inflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareRecycleView()
        observeFavorites()
        val itemTouchHelper = object :ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean{
                return  true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
            viewModel.deleteMeal(favoriteMealAdapter.differ.currentList[position])
                Snackbar.make(requireView(),"Meal deleted",Snackbar.LENGTH_LONG).setAction(
                    "Undo"
                ) {
                    viewModel.insertMeal(favoriteMealAdapter.differ.currentList[position])
                }.show()
            }
        }
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.recViewFavorite)
    }

    private fun prepareRecycleView() {
        favoriteMealAdapter=FavoriteMealAdapter()
        binding.recViewFavorite.apply {
            layoutManager =GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter=favoriteMealAdapter
        }
    }

    private fun observeFavorites() {
        viewModel.observeFavoriteLiveData().observe(requireActivity(), Observer {
            meals->
            favoriteMealAdapter.differ.submitList(meals)
        })
    }
}