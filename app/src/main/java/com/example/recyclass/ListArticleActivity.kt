package com.example.recyclass

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import com.example.recyclass.databinding.ActivityListArticleBinding
import com.example.recyclass.databinding.LayoutArticleBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior

class ListArticleActivity : AppCompatActivity() {
    private lateinit var layoutArticle: LinearLayout

    private lateinit var binding: ActivityListArticleBinding
    private lateinit var bindingLayoutArticleBinding: LayoutArticleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        View.noActionBar(window, supportActionBar)

        layoutArticle = findViewById(R.id.layout_article)
        bindingLayoutArticleBinding = LayoutArticleBinding.bind(layoutArticle)
        val sheetBehavior = BottomSheetBehavior.from(layoutArticle)

        bindingLayoutArticleBinding.btnArrow.setOnClickListener {
            if (sheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
                sheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            } else {
                sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }

        sheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: android.view.View, newState: Int) {}

            override fun onSlide(bottomSheet: android.view.View, slideOffset: Float) {
                bindingLayoutArticleBinding.btnArrow.rotation = (slideOffset * 180)
            }
        })
    }
}