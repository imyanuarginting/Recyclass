package com.example.recyclass.ui.listarticle

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recyclass.data.dataclass.PlasticTypeResponse
import com.example.recyclass.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListArticleViewModel : ViewModel() {
    private val _plasticType = MutableLiveData<String>()
    private val plasticType: LiveData<String> = _plasticType

    fun getPlasticType() : LiveData<String> {
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
        return plasticType
    }

    companion object {
        const val TAG = "ListArticleViewModel"
    }
}