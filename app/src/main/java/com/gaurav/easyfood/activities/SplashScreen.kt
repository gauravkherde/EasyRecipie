package com.gaurav.easyfood.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(com.gaurav.easyfood.R.layout.activity_splash_screenn)
        val imageView = findViewById<ImageView>(com.gaurav.easyfood.R.id.imageView);
        val textView1 = findViewById<TextView>(com.gaurav.easyfood.R.id.textView);
        val top = AnimationUtils.loadAnimation(this, com.gaurav.easyfood.R.anim.top)
        val bottom = AnimationUtils.loadAnimation(this, com.gaurav.easyfood.R.anim.bottom)
        imageView.animation = top
        textView1.animation = bottom

        Handler().postDelayed(Runnable {
            val intent = Intent(this@SplashScreen, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 2500)
    }
}