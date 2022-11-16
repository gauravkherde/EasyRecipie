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
import com.gaurav.easyfood.adapter.CategoriesAdapter
import com.gaurav.easyfood.databinding.FragmentCategoriesBinding
import com.gaurav.easyfood.pojo.Category
import com.gaurav.easyfood.viewModels.HomeViewModel


class CategoriesFragment : Fragment() {

    private  lateinit var binding:FragmentCategoriesBinding
    private  lateinit var categoryAdapter:CategoriesAdapter
    private lateinit var viewModel: HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        categoryAdapter= CategoriesAdapter()
          viewModel= (activity as MainActivity).viewModel

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentCategoriesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareRecyclerView()
        observeCategories()

    }

    private fun observeCategories() {
        viewModel.observeCategoriesLiveData().observe(viewLifecycleOwner, Observer
        { categories ->
            categoryAdapter.setCategoriesList(categories)
        })

    }

    private fun prepareRecyclerView() {

        binding.recViewCategory.apply {
            layoutManager=GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
            adapter=categoryAdapter
        }
    }
}