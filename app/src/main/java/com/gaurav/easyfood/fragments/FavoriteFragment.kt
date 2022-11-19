package com.gaurav.easyfood.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.gaurav.easyfood.activities.MainActivity
import com.gaurav.easyfood.activities.MealActivity
import com.gaurav.easyfood.adapter.FavoriteMealAdapter
import com.gaurav.easyfood.databinding.FragmentFavoritesBinding
import com.gaurav.easyfood.viewModels.HomeViewModel
import com.google.android.material.snackbar.Snackbar

class FavoriteFragment : Fragment() {

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
        onMealByCategoryClick()
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
                val meal=favoriteMealAdapter.differ.currentList[position]
            viewModel.deleteMeal(meal)
                Snackbar.make(requireView(),"Meal deleted",Snackbar.LENGTH_LONG).setAction("Undo")
                {
                     viewModel.insertMeal(meal)
                }.show()
            }
        }
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.recViewFavorite)
    }
    private fun onMealByCategoryClick() {
        favoriteMealAdapter.onItemClick={

            val intent = Intent(activity , MealActivity::class.java)
            intent.putExtra(HomeFragment.MEAL_ID, it.idMeal)
            intent.putExtra(HomeFragment.MEAL_NAME, it.strMeal)
            intent.putExtra(HomeFragment.MEAL_THUMB, it.strMealThumb)
            startActivity(intent)
        }
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