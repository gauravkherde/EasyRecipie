package com.gaurav.easyfood.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.gaurav.easyfood.fragments.HomeFragment


class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        setContentView(com.gaurav.easyfood.R.layout.activity_splash_screenn)
        val imageView = findViewById<ImageView>(com.gaurav.easyfood.R.id.imageView);
        val textView1 = findViewById<TextView>(com.gaurav.easyfood.R.id.textView);
        val top = AnimationUtils.loadAnimation(this, com.gaurav.easyfood.R.anim.top)
        val bottom = AnimationUtils.loadAnimation(this, com.gaurav.easyfood.R.anim.bottom)
        imageView.animation = top
        textView1.animation = bottom

        Handler().postDelayed(Runnable {
            if (intent.extras != null) {
                val mealName = intent.extras?.getString("MEAL_NAME")
                val mealId = intent.extras?.getString("MEAL_ID")
                val mealThumb = intent.extras?.getString("MEAL_THUMB")
                Log.d("notificationSplashData", mealName.toString() + "" + mealId.toString() + "" + mealThumb.toString())
                val intent = Intent(applicationContext, MealActivity::class.java)
                intent.putExtra(HomeFragment.MEAL_ID, mealId)
                intent.putExtra(HomeFragment.MEAL_NAME, mealName)
                intent.putExtra(HomeFragment.MEAL_THUMB, mealThumb)
                startActivity(intent)
            } else {
                val intent = Intent(this@SplashScreen, MainActivity::class.java)
                startActivity(intent)
            }
        }, 1500)
    }
}