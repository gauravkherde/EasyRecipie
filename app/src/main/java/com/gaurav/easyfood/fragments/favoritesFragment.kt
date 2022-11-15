package com.gaurav.easyfood.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.gaurav.easyfood.R
import com.gaurav.easyfood.activities.MainActivity
import com.gaurav.easyfood.adapter.FavoriteMealAdapter
import com.gaurav.easyfood.databinding.FragmentFavoritesBinding
import com.gaurav.easyfood.databinding.FragmentHomeBinding
import com.gaurav.easyfood.viewModels.HomeViewModel

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