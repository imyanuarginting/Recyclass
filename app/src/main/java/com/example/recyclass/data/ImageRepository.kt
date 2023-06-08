package com.example.recyclass.data

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.recyclass.data.dataclass.ImageUploadResponse
import com.example.recyclass.data.dataclass.PlasticTypeResponse
import com.example.recyclass.data.retrofit.ApiConfig
import com.example.recyclass.ui.listarticle.ListArticleViewModel
import com.example.recyclass.ui.main.MainViewModel
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ImageRepository {
    fun uploadImage(image: MultipartBody.Part) {
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

                    }
                }
            }

            override fun onFailure(call: Call<ImageUploadResponse>, t: Throwable) {
                Log.d(MainViewModel.TAG, t.message.toString())
            }
        })
    }

//    fun getPlasticType() : LiveData<String> {
//        val apiService = ApiConfig().getApiService()
//        val response = apiService.getPlasticType()
//        response.enqueue(object: Callback<PlasticTypeResponse> {
//            override fun onResponse(
//                call: Call<PlasticTypeResponse>,
//                response: Response<PlasticTypeResponse>
//            ) {
//                if (response.isSuccessful) {
//                    val responseBody = response.body()
//                    if (responseBody != null) {
//                        if (!responseBody.error) {
////                            _plasticType.value = responseBody.result
//                        }
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<PlasticTypeResponse>, t: Throwable) {
//                Log.d(ListArticleViewModel.TAG, t.message.toString())
//            }
//        })
////        return plasticType
//    }
}