package com.gaurav.easyfood.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gaurav.easyfood.activities.CategoryMealsActivity
import com.gaurav.easyfood.databinding.MealItemBinding
import com.gaurav.easyfood.pojo.MealByCategory
import com.gaurav.easyfood.pojo.MealList

class CategoryMealAdapter : RecyclerView.Adapter<CategoryMealAdapter.CategoryMealViewModel>() {
    var mealList=ArrayList<MealByCategory>()

     fun setMealList(mealList: List<MealByCategory>){
         this.mealList= mealList as ArrayList<MealByCategory>
         notifyDataSetChanged()
     }

    inner  class CategoryMealViewModel(var binding: MealItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMealViewModel {
        return  CategoryMealViewModel(MealItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: CategoryMealViewModel, position: Int) {
        Glide.with(holder.itemView)
            .load(mealList[position].strMealThumb)
            .into(holder.binding.imgMeal)
         holder.binding.tvMealName.text=mealList[position].strMeal

    }

    override fun getItemCount(): Int {
        return  mealList.size
    }

}