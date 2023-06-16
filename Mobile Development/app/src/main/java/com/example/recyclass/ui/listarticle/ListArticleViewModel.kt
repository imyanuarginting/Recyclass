package com.example.recyclass.ui.listarticle

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.recyclass.data.ArticleRepository
import com.example.recyclass.data.dataclass.Article
import com.example.recyclass.data.retrofit.ApiConfig

class ListArticleViewModel(plastic_type: String) : ViewModel() {

    private val articleRepository = ArticleRepository(ApiConfig().getApiService())

    val articles: LiveData<PagingData<Article>> = articleRepository.getArticle(plastic_type).cachedIn(viewModelScope)

    companion object {
        const val TAG = "ListArticleViewModel"
    }
}