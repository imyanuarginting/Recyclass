package com.example.recyclass.data.retrofit

import com.example.recyclass.data.dataclass.ArticleResponse
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

    @GET("/classification-result")
    fun getPlasticType(): Call<PlasticTypeResponse>

    @GET("/articles")
    suspend fun getArticles(
        @Query("plastic_type") plastic_type: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): ArticleResponse
}

class ApiConfig {
    fun getApiService(): ApiService {
//        val loggingInterceptor = if (BuildConfig.DEBUG) {
//            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
//        }
//        else {
//            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
//        }

        val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://recyclass-hsmqsadgbq-et.a.run.app")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        return retrofit.create(ApiService::class.java)
    }
}