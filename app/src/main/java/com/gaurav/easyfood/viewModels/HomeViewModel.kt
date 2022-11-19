package com.gaurav.easyfood.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaurav.easyfood.db.MealDataBase
import com.gaurav.easyfood.pojo.*
import com.gaurav.easyfood.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private var mealDataBase: MealDataBase) : ViewModel() {
    private var randomMealLiveData = MutableLiveData<Meal>()
    private var popularItemLiveData = MutableLiveData<List<MealByCategory>>()
    private var categoriesLiveData = MutableLiveData<List<Category>>()
    private var favoriteMeaLiveData = mealDataBase.mealDao().getAllMeals()
    private var bottomSheetLiveData = MutableLiveData<Meal>()
    fun getRandomMeal() {
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null) {
                    val randomMeal: Meal = response.body()!!.meals[0]
                    randomMealLiveData.value=randomMeal
                } else return
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("HomeFragment", t.message.toString())
            }

        })
    }

    fun observeRandomMealLiveData():LiveData<Meal>{
        return randomMealLiveData
    }
    fun observePopularItemsLiveData(): LiveData<List<MealByCategory>> {
        return popularItemLiveData
    }
    fun observeCategoriesLiveData(): LiveData<List<Category>> {
        return categoriesLiveData
    }

    fun observeFavoriteLiveData(): LiveData<List<Meal>> {
        return favoriteMeaLiveData
    }
    fun observeBottomSheetMealLiveData(): LiveData<Meal> {
        return bottomSheetLiveData
    }

    fun getPopularItems(){
        RetrofitInstance.api.getPopularOption("Vegetarian").enqueue(object  :Callback<MealByCategoryList>{
            override fun onResponse(call: Call<MealByCategoryList>, response: Response<MealByCategoryList>) {
              if (response.body()!=null)
              {
                  popularItemLiveData.value= response.body()!!.meals
              }
            }

            override fun onFailure(call: Call<MealByCategoryList>, t: Throwable) {
                Log.d("HomeFragment", t.message.toString())
            }

        })
    }

    fun getCategories(){
        RetrofitInstance.api.getCategories().enqueue(object  :Callback<CategoryList>{
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
               response.body()?.let {
                   categoriesLiveData.postValue(it.categories)
               }

            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.d("HomeFragment", t.message.toString())
            }

        })
    }
    fun deleteMeal(meal: Meal){
        viewModelScope.launch {
            mealDataBase.mealDao().deleteMeal(meal)
        }
    }
    fun insertMeal(meal: Meal){
        viewModelScope.launch {
            mealDataBase.mealDao().upsertMeal(meal)
        }
    }

    fun getMealById(id: String){
        RetrofitInstance.api.getMealDetail(id).enqueue(object : Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                val meal= response.body()?.meals?.first()
                meal?.let {
                    bottomSheetLiveData.postValue(it)
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("HomeViewModel",t.message.toString())
            }

        })
    }
}