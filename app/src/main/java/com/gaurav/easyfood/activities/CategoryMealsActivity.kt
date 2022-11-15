package com.gaurav.easyfood.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.gaurav.easyfood.R
import com.gaurav.easyfood.adapter.CategoryMealAdapter
import com.gaurav.easyfood.databinding.ActivityCategoryMealsBinding
import com.gaurav.easyfood.fragments.HomeFragment
import com.gaurav.easyfood.viewModels.CategoryMealViewModel

class CategoryMealsActivity : AppCompatActivity() {
    lateinit var binding: ActivityCategoryMealsBinding
    lateinit var categoryViewModel:CategoryMealViewModel
    lateinit var categoryMealAdapter: CategoryMealAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prepareRecyclerView()

        categoryViewModel=ViewModelProvider(this)[CategoryMealViewModel::class.java]
        categoryViewModel.getMealByCategory(intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!)
        categoryViewModel.observeMealsLiveData().observe(this,Observer{
            binding.tvCategoryCount.text=it.size.toString()
            categoryMealAdapter.setMealList(it)
        })
    }
    fun prepareRecyclerView(){
        categoryMealAdapter= CategoryMealAdapter()
        binding.recViewCategoryMeal.apply {
            layoutManager=GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter=categoryMealAdapter
        }
    }
}