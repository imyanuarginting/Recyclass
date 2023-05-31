package com.example.recyclass

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.recyclass.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        View.noActionBar(window, supportActionBar)

        Handler().postDelayed({
            val intent = Intent(this@SplashScreenActivity, SplashScreenContinueActivity::class.java)
            startActivity(intent)
        }, 500)
    }
}