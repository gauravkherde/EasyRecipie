package com.gaurav.easyfood.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.gaurav.easyfood.activities.CategoryMealsActivity
import com.gaurav.easyfood.activities.MainActivity
import com.gaurav.easyfood.activities.MealActivity
import com.gaurav.easyfood.adapter.CategoriesAdapter
import com.gaurav.easyfood.adapter.MostPopularAdapter
import com.gaurav.easyfood.databinding.FragmentHomeBinding
import com.gaurav.easyfood.fragments.bottomsheet.MealBottomSheetFragment
import com.gaurav.easyfood.pojo.MealByCategory
import com.gaurav.easyfood.pojo.Meal
import com.gaurav.easyfood.viewModels.HomeViewModel


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var randomMeal: Meal
    private lateinit var popularItemAdapter: MostPopularAdapter
    private lateinit var categoriesAdapter: CategoriesAdapter

    companion object {
        const val MEAL_ID = "com.gaurav.easyfood.fragments.idMeal"
        const val MEAL_NAME = "com.gaurav.easyfood.fragments.nameMeal"
        const val MEAL_THUMB = "com.gaurav.easyfood.fragments.thumbMeal"
        const val CATEGORY_NAME = "com.gaurav.easyfood.fragments.categoryName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel =(activity as MainActivity).viewModel
       // homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        popularItemAdapter = MostPopularAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preparePopularItemRecyclerView()
        viewModel.getRandomMeal()
        observerRandomMeal()
        onRandomMealClick()
        viewModel.getPopularItems()
        viewModel.getCategories()
        observerPopularItems()
        onPopularItemClick()
        onPopularItemLongClick()
        observerCategories()
        prepareCategoriesRecyclerView()
        onCategoriesClick()
    }

    private fun onPopularItemLongClick() {
        popularItemAdapter.onLongItemClick={
            var mealBottomSheetFragment= MealBottomSheetFragment.newInstance(it.idMeal)
            mealBottomSheetFragment.show(childFragmentManager,"Meal Info")
        }
    }


    private fun prepareCategoriesRecyclerView() {
        categoriesAdapter = CategoriesAdapter()
        binding.recViewCategory.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = categoriesAdapter
        }
    }

    private fun observerCategories() {
        viewModel.observeCategoriesLiveData().observe(viewLifecycleOwner, Observer
        { categories ->
            categoriesAdapter.setCategoriesList(categories)
        })

    }

    private fun onPopularItemClick() {
        popularItemAdapter.onItemClick = {
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, it.idMeal)
            intent.putExtra(MEAL_NAME, it.strMeal)
            intent.putExtra(MEAL_THUMB, it.strMealThumb)
            startActivity(intent)
        }
    }


    private fun onCategoriesClick() {
        categoriesAdapter.onItemCick={
            val intent=Intent(activity,CategoryMealsActivity::class.java)
            intent.putExtra(CATEGORY_NAME,it.strCategory)
            startActivity(intent)
        }
    }
    private fun preparePopularItemRecyclerView() {
        binding.recViewMealsPopular.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularItemAdapter
        }
    }

    private fun observerPopularItems() {
        viewModel.observePopularItemsLiveData().observe(viewLifecycleOwner)
        { mealList ->

            popularItemAdapter.setMeals(mealList = mealList as ArrayList<MealByCategory>)
        }
    }

    private fun onRandomMealClick() {
        binding.imgRandomMealCard.setOnClickListener {
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, randomMeal.idMeal)
            intent.putExtra(MEAL_NAME, randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB, randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observerRandomMeal() {
        viewModel.observeRandomMealLiveData().observe(viewLifecycleOwner)
        { meal ->
            Glide.with(this@HomeFragment)
                .load(meal!!.strMealThumb)
                .into(binding.imgRandomMeal)
            this.randomMeal = meal
        }
    }
}