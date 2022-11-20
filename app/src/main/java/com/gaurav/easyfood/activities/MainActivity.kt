package com.gaurav.easyfood.activities

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.gaurav.easyfood.R
import com.gaurav.easyfood.db.MealDataBase
import com.gaurav.easyfood.viewModels.HomeViewModel
import com.gaurav.easyfood.viewModels.HomeViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
     val  viewModel: HomeViewModel by lazy {
         val mealDataBase =MealDataBase.getInstance(this)
         val homeViewModelProviderFactory =HomeViewModelFactory(mealDataBase)
         ViewModelProvider(this,homeViewModelProviderFactory)[HomeViewModel::class.java]
     }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNav)
        val navController =Navigation.findNavController(this, R.id.mainFragment)
        NavigationUI.setupWithNavController(bottomNavigation, navController )
    }
}