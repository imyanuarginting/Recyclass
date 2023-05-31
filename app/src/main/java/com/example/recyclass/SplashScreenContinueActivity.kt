package com.example.recyclass

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.recyclass.databinding.ActivitySplashScreenContinueBinding

class SplashScreenContinueActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenContinueBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenContinueBinding.inflate(layoutInflater)
        setContentView(binding.root)

        View.noActionBar(window, supportActionBar)

        Handler().postDelayed({

        }, 3000)
    }
}