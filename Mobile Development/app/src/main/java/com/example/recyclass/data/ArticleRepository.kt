package com.example.recyclass.data

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.recyclass.data.dataclass.Article
import com.example.recyclass.data.pagingforarticles.PagingSource
import com.example.recyclass.data.retrofit.ApiService

class ArticleRepository(private val apiService: ApiService) {
    fun getArticle(plastic_type: String) : LiveData<PagingData<Article>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                PagingSource(apiService, plastic_type)
            }
        ).liveData
    }
}