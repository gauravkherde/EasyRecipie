package com.gaurav.easyfood.fragments

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.gaurav.easyfood.R
import com.gaurav.easyfood.activities.MealActivity
import com.gaurav.easyfood.adapter.MostPopularAdapter
import com.gaurav.easyfood.databinding.FragmentSearchBinding
import com.gaurav.easyfood.fragments.HomeFragment.Companion.MEAL_ID
import com.gaurav.easyfood.fragments.HomeFragment.Companion.MEAL_NAME
import com.gaurav.easyfood.fragments.HomeFragment.Companion.MEAL_THUMB
import com.gaurav.easyfood.pojo.Meal
import com.gaurav.easyfood.viewModels.SearchViewModel

class SearchFragment : Fragment() {
    private lateinit var myAdapter: MostPopularAdapter
    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchMvvm: SearchViewModel
    private var mealId = ""
    private var mealStr = ""
    private var mealThumb = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myAdapter = MostPopularAdapter()
        searchMvvm = ViewModelProviders.of(this)[SearchViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onSearchClick()
        onEnterClickOnKeyBoard()
        observeSearchLiveData()
        setOnMealCardClick()
    }


    private fun setOnMealCardClick() {
        binding.searchedMealCard.setOnClickListener {
            val intent = Intent(context, MealActivity::class.java)

            intent.putExtra(MEAL_ID, mealId)
            intent.putExtra(MEAL_NAME, mealStr)
            intent.putExtra(MEAL_THUMB, mealThumb)

            startActivity(intent)


        }
    }

    private fun onEnterClickOnKeyBoard() {
        binding.edSearch.setOnKeyListener(View.OnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                searchMvvm.searchMealDetail(binding.edSearch.text.toString(), context)
                return@OnKeyListener true
            }
            false
        })
    }

    private fun onSearchClick() {
        binding.icSearch.setOnClickListener {
            searchMvvm.searchMealDetail(binding.edSearch.text.toString(), context)

        }
    }

    private fun observeSearchLiveData() {
        searchMvvm.observeSearchLiveData()
            .observe(
                viewLifecycleOwner
            ) { t ->
                if (t == null) {
                    Toast.makeText(context, "No such a meal", Toast.LENGTH_SHORT).show()
                } else {
                    binding.apply {

                        mealId = t.idMeal
                        mealStr = t.strMeal.toString()
                        mealThumb = t.strMealThumb.toString()

                        Glide.with(context!!.applicationContext)
                            .load(t.strMealThumb)
                            .into(imgSearchedMeal)

                        tvSearchedMeal.text = t.strMeal
                        searchedMealCard.visibility = View.VISIBLE
                    }
                }
            }
    }


}