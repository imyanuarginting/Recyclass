package com.example.recyclass.ui.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.recyclass.View
import com.example.recyclass.databinding.ActivitySplashScreenContinueBinding
import com.example.recyclass.ui.main.MainActivity

class SplashScreenContinueActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenContinueBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenContinueBinding.inflate(layoutInflater)
        setContentView(binding.root)

        View.noActionBar(window, supportActionBar)

        Handler().postDelayed({
            val intent = Intent(this@SplashScreenContinueActivity, MainActivity::class.java)
            startActivity(intent)
        }, 3000)
    }
}