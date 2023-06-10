package com.example.recyclass.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.recyclass.data.dataclass.ImageUploadResponse
import com.example.recyclass.data.dataclass.PlasticTypeResponse
import com.example.recyclass.data.retrofit.ApiConfig
import com.example.recyclass.ui.listarticle.ListArticleViewModel
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ImageRepository {
    private val indicator = MediatorLiveData<Indicator<String>>()

    fun uploadImage(image: MultipartBody.Part) : LiveData<Indicator<String>> {
        indicator.value = Indicator.Loading
        val apiService = ApiConfig().getApiService()
        val response = apiService.uploadImage(image)
        response.enqueue(object: Callback<ImageUploadResponse> {
            override fun onResponse(
                call: Call<ImageUploadResponse>,
                response: Response<ImageUploadResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        getPlasticType()
                    }
                }
            }

            override fun onFailure(call: Call<ImageUploadResponse>, t: Throwable) {
                indicator.value = Indicator.Error(t.message.toString())
                Log.d(ListArticleViewModel.TAG, t.message.toString())
            }
        })
        return indicator
    }

    fun getPlasticType() {
        indicator.value = Indicator.Loading
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
                            indicator.value = Indicator.Success(responseBody.result)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<PlasticTypeResponse>, t: Throwable) {
                indicator.value = Indicator.Error(t.message.toString())
                Log.d(ListArticleViewModel.TAG, t.message.toString())
            }
        })
    }
}

sealed class Indicator<out T> {
    data class Success(val plasticType: String) : Indicator<String>()
    data class Error(val error: String) : Indicator<String>()
    object Loading : Indicator<Nothing>()
}