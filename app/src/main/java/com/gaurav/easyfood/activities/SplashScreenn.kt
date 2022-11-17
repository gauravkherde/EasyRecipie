package com.gaurav.easyfood.activities

import android.R
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class SplashScreenn : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(com.gaurav.easyfood.R.layout.activity_splash_screenn)
        var imageView = findViewById<ImageView>(com.gaurav.easyfood.R.id.imageView);
        var textView1 = findViewById<TextView>(com.gaurav.easyfood.R.id.textView);
        var top = AnimationUtils.loadAnimation(this, com.gaurav.easyfood.R.anim.top)
        var bottom = AnimationUtils.loadAnimation(this, com.gaurav.easyfood.R.anim.bottom)
        imageView.setAnimation(top)
        textView1.setAnimation(bottom)

        Handler().postDelayed(Runnable {
            val intent = Intent(this@SplashScreenn, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 2500)
    }
}