package com.example.recyclass.ui.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.recyclass.SettingApplication
import com.example.recyclass.databinding.ActivitySplashScreenContinueBinding
import com.example.recyclass.ui.camera.CameraActivity

class SplashScreenContinueActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenContinueBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenContinueBinding.inflate(layoutInflater)
        setContentView(binding.root)

        SettingApplication.noActionBar(window, supportActionBar)

        Handler().postDelayed({
            val intent = Intent(this@SplashScreenContinueActivity, CameraActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}