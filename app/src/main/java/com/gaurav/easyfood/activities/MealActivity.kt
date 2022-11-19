package com.gaurav.easyfood.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.gaurav.easyfood.R
import com.gaurav.easyfood.databinding.ActivityMealBinding
import com.gaurav.easyfood.db.MealDataBase
import com.gaurav.easyfood.fragments.HomeFragment
import com.gaurav.easyfood.pojo.Meal
import com.gaurav.easyfood.viewModels.MealViewModel
import com.gaurav.easyfood.viewModels.MealViewModelFactory

class MealActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMealBinding
    private lateinit var mealId: String
    private lateinit var mealName: String
    private lateinit var mealThumb: String
    private lateinit var youtubeLink: String
    private lateinit var mealViewModel: MealViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val mealDataBase=MealDataBase.getInstance(this)
        val viewModelFactory =MealViewModelFactory(mealDataBase)
        mealViewModel =ViewModelProvider(this,viewModelFactory)[MealViewModel::class.java]
       // mealViewModel = ViewModelProvider(this)[MealViewModel::class.java]
        getMealInfoFromIntent()
        setInfoInViews()
        loadingCase()
        mealViewModel.getMealDetail(mealId)
        observeMealDetailsLiveData()
        onYoutubeImageClick()
        onFavoriteCLick()
    }

    private fun onFavoriteCLick() {
        binding.buttonAddToFav.setOnClickListener {
            mealToSave?.let {
                mealViewModel.insertMeal(it)
                Toast.makeText(this,"Meal Saved",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onYoutubeImageClick() {
        binding.imgYoutube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }

    private  var mealToSave:Meal?=null
    private fun observeMealDetailsLiveData() {
        mealViewModel.observeMealDetailsLiveData().observe(this
        ) { meal ->
            onResponseCase()
            mealToSave = meal
            binding.tvCategory.text = "Category : ${meal!!.strCategory}"
            binding.tvArea.text = "Cuisine : ${meal!!.strArea}"
            binding.tvInstructionDetails.text = meal.strInstructions
            youtubeLink = meal.strYoutube!!
        }
    }

    private fun setInfoInViews() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMealDetail)
        binding.collapsingToolbar.title = mealName
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))
    }

    private fun getMealInfoFromIntent() {
        val intent = intent
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!
    }

    private fun loadingCase() {
        binding.progressBar.visibility = View.VISIBLE
        binding.buttonAddToFav.visibility = View.INVISIBLE
        binding.tvInstruction.visibility = View.INVISIBLE
        binding.imgMealDetail.visibility = View.INVISIBLE
        binding.tvCategory.visibility = View.INVISIBLE
        binding.tvArea.visibility = View.INVISIBLE
        binding.imgYoutube.visibility = View.INVISIBLE
    }

    private fun onResponseCase() {
        binding.progressBar.visibility = View.INVISIBLE
        binding.buttonAddToFav.visibility = View.VISIBLE
        binding.tvInstruction.visibility = View.VISIBLE
        binding.imgMealDetail.visibility = View.VISIBLE
        binding.tvCategory.visibility = View.VISIBLE
        binding.tvArea.visibility = View.VISIBLE
        binding.imgYoutube.visibility = View.VISIBLE
    }
}