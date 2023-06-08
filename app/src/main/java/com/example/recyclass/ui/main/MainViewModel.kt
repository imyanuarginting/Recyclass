package com.example.recyclass.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recyclass.data.dataclass.ImageUploadResponse
import com.example.recyclass.data.retrofit.ApiConfig
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _success = MutableLiveData<Boolean>()
    val success: LiveData<Boolean> = _success
    var ready = false

    init {
        ready = true
    }
    fun uploadImage(image: MultipartBody.Part) {
        _success.postValue(false)
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
                        _success.postValue(true)
                    }
                }
            }

            override fun onFailure(call: Call<ImageUploadResponse>, t: Throwable) {
                _success.postValue(true)
                Log.d(TAG, t.message.toString())
            }
        })
    }

    companion object {
        const val TAG = "MainViewModel"
    }
}