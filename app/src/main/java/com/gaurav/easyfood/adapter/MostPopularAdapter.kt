package com.gaurav.easyfood.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gaurav.easyfood.databinding.PopularItemBinding
import com.gaurav.easyfood.pojo.CategoryList
import com.gaurav.easyfood.pojo.CategoryMeals
import com.gaurav.easyfood.pojo.MealList

class MostPopularAdapter(): RecyclerView.Adapter<MostPopularAdapter.PopularMealViewHolder>() {
    lateinit var onItemClick:((CategoryMeals)->Unit)
    private var  mealList =ArrayList<CategoryMeals>()

    fun setMeals(mealList: ArrayList<CategoryMeals>){
        this.mealList=mealList
        notifyDataSetChanged()
    }
    class PopularMealViewHolder( val binding:PopularItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMealViewHolder {
       return  PopularMealViewHolder(PopularItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: PopularMealViewHolder, position: Int) {
       Glide.with(holder.itemView)
           .load(mealList[position].strMealThumb)
           .into(holder.binding.imagePopularMealItem)
        holder.itemView.setOnClickListener {
            onItemClick.invoke(mealList[position])
        }
    }

    override fun getItemCount(): Int {
        return  mealList.size
    }

}