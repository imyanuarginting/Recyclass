package com.example.recyclass.ui.camera

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.recyclass.data.ImageRepository
import com.example.recyclass.data.Indicator
import okhttp3.MultipartBody

class CameraViewModel : ViewModel() {
    private val repository = ImageRepository()

    fun uploadImage(image: MultipartBody.Part) : LiveData<Indicator<String>> {
        return repository.uploadImage(image)
    }
}