package com.example.recyclass.ui.listarticle

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclass.R
import com.example.recyclass.View
import com.example.recyclass.data.dataclass.Article
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

        viewModel.plasticType.observe(this) {
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

        val layoutManager = LinearLayoutManager(this)
        val adapter = Adapter()
        bindingLayoutArticleBinding.recyclerViewArticleLayoutArticle.layoutManager = layoutManager
        bindingLayoutArticleBinding.recyclerViewArticleLayoutArticle.adapter = adapter

        viewModel.articles.observe(this) {
            adapter.submitData(lifecycle, it)
        }

        adapter.onItemCallback(object : Adapter.OnItemClickCallback {
            override fun onItemClicked(item: Article) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.url))
                startActivity(intent)
            }
        })

        val currentImagePath = intent.getStringExtra(EXTRA_IMAGE)
        val image = File(currentImagePath)
        binding.imageViewArticleList.setImageBitmap(BitmapFactory.decodeFile(image.path))
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("ListActivity", "onDestroy")
    }

    companion object {
        var EXTRA_IMAGE = "image"
    }
}