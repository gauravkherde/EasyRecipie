package com.gaurav.easyfood.fragments

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ProgressBar
import androidx.navigation.fragment.findNavController
import androidx.fragment.app.Fragment
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
import com.gaurav.easyfood.pojo.Category
import com.gaurav.easyfood.pojo.Meal
import com.gaurav.easyfood.pojo.MealByCategory
import com.gaurav.easyfood.viewModels.HomeViewModel
import android.os.CountDownTimer;
import android.os.Handler
import com.gaurav.easyfood.R


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var randomMeal: Meal
    private lateinit var popularItemAdapter: MostPopularAdapter
    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var progressDialog: ProgressDialog
    private lateinit var dialog: Dialog

    companion object {
        const val MEAL_ID = "MEAL_ID"
        const val MEAL_NAME = "MEAL_NAME"
        const val MEAL_THUMB = "MEAL_THUMB"
        const val CATEGORY_NAME = "CATEGORY_NAME"
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
       /* progressDialog = ProgressDialog(requireContext())
        progressDialog.setMessage("Loading")
        progressDialog.show()*/
        dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView( R.layout.progress_custom_dialog);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        Handler().postDelayed(Runnable {
            dialog.dismiss()
        }, 1500)
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

        binding.imgSearch.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }

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
                val list:List<Category> = categories.subList(1,categories.size)
               categoriesAdapter.setCategoriesList(list)
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
            /*progressDialog.hide()*/

            Glide.with(this@HomeFragment)
                .load(meal!!.strMealThumb)
                .into(binding.imgRandomMeal)
            binding.tvRandomMealName.text=meal.strMeal
            this.randomMeal = meal
        }
    }
}