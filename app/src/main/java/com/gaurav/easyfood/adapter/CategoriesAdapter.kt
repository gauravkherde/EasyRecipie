package com.gaurav.easyfood.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gaurav.easyfood.databinding.CategoryItemBinding
import com.gaurav.easyfood.pojo.Category

class CategoriesAdapter():RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {
    private var  categoriesList :List<Category> = ArrayList()
    var onItemCick : ((Category) -> Unit?)? =null

    fun setCategoriesList(categoriesList: List<Category>){
        this.categoriesList=categoriesList
        notifyDataSetChanged()
    }
    inner class  CategoryViewHolder(val binding: CategoryItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        Glide.with(holder.itemView).load(categoriesList[position].strCategoryThumb).into(holder.binding.imgCategory)
        holder.binding.tvCategoryText.text=categoriesList[position].strCategory
        holder.itemView.setOnClickListener {
            onItemCick!!.invoke(categoriesList[position])
        }
    }

    override fun getItemCount(): Int {
      return categoriesList.size
    }
}