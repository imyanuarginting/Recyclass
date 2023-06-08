package com.example.recyclass.ui.listarticle

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.recyclass.data.ArticleRepository
import com.example.recyclass.data.dataclass.Article
import com.example.recyclass.data.dataclass.PlasticTypeResponse
import com.example.recyclass.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListArticleViewModel : ViewModel() {
    private val _plasticType = MutableLiveData<String>()
    val plasticType: LiveData<String> = _plasticType

    private val articleRepository = ArticleRepository(ApiConfig().getApiService())

    init {
        getPlasticType()
    }

    val articles: LiveData<PagingData<Article>> = articleRepository.getArticle().cachedIn(viewModelScope)

    private fun getPlasticType() {
        val apiService = ApiConfig().getApiService()
        val response = apiService.getPlasticType()
        response.enqueue(object: Callback<PlasticTypeResponse> {
            override fun onResponse(
                call: Call<PlasticTypeResponse>,
                response: Response<PlasticTypeResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        if (!responseBody.error) {
                            _plasticType.value = responseBody.result
                        }
                    }
                }
            }

            override fun onFailure(call: Call<PlasticTypeResponse>, t: Throwable) {
                Log.d(TAG, t.message.toString())
            }
        })
    }

    companion object {
        const val TAG = "ListArticleViewModel"
    }
}