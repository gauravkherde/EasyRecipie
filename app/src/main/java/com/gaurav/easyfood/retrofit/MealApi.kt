package com.gaurav.easyfood.retrofit

import com.gaurav.easyfood.pojo.CategoryList
import com.gaurav.easyfood.pojo.MealByCategory
import com.gaurav.easyfood.pojo.MealByCategoryList
import com.gaurav.easyfood.pojo.MealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {

    @GET("random.php")
    fun getRandomMeal():Call<MealList>

    @GET("lookup.php?")
    fun getMealDetail(@Query("i")id:String):Call<MealList>

    @GET("filter.php?")
    fun getPopularOption(@Query("c")categoryName:String):Call<MealByCategoryList>

    @GET("categories.php")
    fun getCategories(): Call<CategoryList>

    @GET("filter.php")
    fun mealByCategory(@Query("c")categoryName: String):Call<MealByCategoryList>
}