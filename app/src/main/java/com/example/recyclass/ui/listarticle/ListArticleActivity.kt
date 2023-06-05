package com.example.recyclass.ui.listarticle

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import com.example.recyclass.R
import com.example.recyclass.View
import com.example.recyclass.databinding.ActivityListArticleBinding
import com.example.recyclass.databinding.LayoutArticleBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import java.io.File

class ListArticleActivity : AppCompatActivity() {
    private lateinit var layoutArticle: LinearLayout
    private lateinit var binding: ActivityListArticleBinding
    private lateinit var bindingLayoutArticleBinding: LayoutArticleBinding
    private lateinit var viewModel: ListArticleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        View.noActionBar(window, supportActionBar)

        viewModel = ListArticleViewModel()

        viewModel.getPlasticType().observe(this) {
            binding.textViewTipePlastik.text = it
        }

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

        val currentImagePath = intent.getStringExtra(EXTRA_IMAGE)
        val image = File(currentImagePath)
        binding.imageViewArticleList.setImageBitmap(BitmapFactory.decodeFile(image.path))
    }

    companion object {
        var EXTRA_IMAGE = "image"
    }
}