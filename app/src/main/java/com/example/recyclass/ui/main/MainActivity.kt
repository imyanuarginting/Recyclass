package com.example.recyclass.ui.main

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.FileProvider
import com.example.recyclass.databinding.ActivityMainBinding
import com.example.recyclass.ui.listarticle.ListArticleActivity
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val timeStamp = SimpleDateFormat(
        TIME_STAMP_FORMAT,
        Locale.US
    ).format(System.currentTimeMillis())
    private lateinit var currentImagePath: String
    private var getImage: File? = null
    private lateinit var splashScreenContent: ConstraintLayout

    private val launcherCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val image = File(currentImagePath)
            image.let {
                getImage = it
                binding.imageView.setImageBitmap(BitmapFactory.decodeFile(it.path))
            }
        }
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = MainViewModel()

        binding.btnCamera.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.resolveActivity(packageManager)

            createTempFile(application).also {
                val imageURI = FileProvider.getUriForFile(
                    this@MainActivity,
                "com.example.recyclass",
                    it
                )
                currentImagePath = it.absolutePath
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageURI)
                File(currentImagePath).deleteOnExit()
                launcherCamera.launch(intent)
            }
        }

        binding.btnUnggah.setOnClickListener {
            if (getImage != null) {
                val image = getImage as File
                val requestImage = image.asRequestBody("image/jpeg".toMediaType())
                val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                    "file",
                    image.name,
                    requestImage
                )
                viewModel.uploadImage(imageMultipart)
            }
        }

        viewModel.success.observe(this) {
            if (it) {
                val intent = Intent(this@MainActivity, ListArticleActivity::class.java)
                intent.putExtra(ListArticleActivity.EXTRA_IMAGE, currentImagePath)
                startActivity(intent)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("MainActivity", "onStart")
    }

    override fun onStop() {
        super.onStop()
        Log.d("MainActivity", "onStop")
    }

    private fun createTempFile(context: Context): File {
        val storageDirectory = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("plastic $timeStamp", ".jpg", storageDirectory)
    }

    companion object {
        private const val TIME_STAMP_FORMAT = "dd-MMM-yyyy HH:mm:ss"
    }
}