package com.gaurav.easyfood.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gaurav.easyfood.pojo.MealByCategory
import com.gaurav.easyfood.pojo.MealByCategoryList
import com.gaurav.easyfood.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryMealViewModel():ViewModel() {

    var mealLiveData= MutableLiveData<List<MealByCategory>>()
    fun getMealByCategory(categoryName :String){
        RetrofitInstance.api.mealByCategory(categoryName).enqueue(object:Callback<MealByCategoryList>{
            override fun onResponse(
                call: Call<MealByCategoryList>,
                response: Response<MealByCategoryList>
            ) {
                response.body()?.let{
                    mealLiveData.postValue(it.meals)
                }
            }

            override fun onFailure(call: Call<MealByCategoryList>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun observeMealsLiveData():LiveData<List<MealByCategory>>
    {return mealLiveData
    }
}