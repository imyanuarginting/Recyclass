package com.example.recyclass.data.retrofit

import androidx.viewbinding.BuildConfig
import com.example.recyclass.data.dataclass.ImageUploadResponse
import com.example.recyclass.data.dataclass.PlasticTypeResponse
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiService {
    @Multipart
    @POST("/upload-photo")
    fun uploadImage(
        @Part file: MultipartBody.Part
    ): Call<ImageUploadResponse>

    @GET("classification-result")
    fun getPlasticType(): Call<PlasticTypeResponse>
}

class ApiConfig {
    fun getApiService(): ApiService {
        val loggingInterceptor = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        }
        else {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://recyclasstrial2-qr35w5quvq-uc.a.run.app")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        return retrofit.create(ApiService::class.java)
    }
}