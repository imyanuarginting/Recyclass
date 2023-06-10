package com.example.recyclass.ui.camera

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import com.example.recyclass.SettingApplication
import com.example.recyclass.data.Indicator
import com.example.recyclass.databinding.ActivityCameraBinding
import com.example.recyclass.ui.listarticle.ListArticleActivity
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class CameraActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCameraBinding
    private val timeStamp = SimpleDateFormat(
        TIME_STAMP_FORMAT,
        Locale.US
    ).format(System.currentTimeMillis())
    private lateinit var currentImagePath: String
    private var getImage: File? = null

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

    private var plasticType = ""

    private lateinit var viewModel: CameraViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        SettingApplication.noActionBar(window, supportActionBar)

        viewModel = CameraViewModel()

        binding.btnCamera.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.resolveActivity(packageManager)

            createTempFile(application).also {
                val imageURI = FileProvider.getUriForFile(
                    this@CameraActivity,
                    "com.example.recyclass",
                    it
                )
                currentImagePath = it.absolutePath
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageURI)
                File(currentImagePath).deleteOnExit()
                launcherCamera.launch(intent)
            }
        }

        binding.btnUpload.setOnClickListener {
            if (getImage != null) {
                val image = getImage as File
                val requestImage = image.asRequestBody("image/jpeg".toMediaType())
                val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                    "file",
                    image.name,
                    requestImage
                )
                viewModel.uploadImage(imageMultipart).observe(this) {
                    when (it) {
                        is Indicator.Success -> {
                            with(binding) {
                                progressBarCamera.visibility = View.GONE
                                btnNext.isEnabled = true
                                btnCamera.isEnabled = false
                                btnGallery.isEnabled = false
                                btnUpload.isEnabled = false
                            }
                            plasticType = it.plasticType
                        }
                        is Indicator.Loading -> {
                            binding.progressBarCamera.visibility = View.VISIBLE
                        }
                        is Indicator.Error -> {
                            binding.progressBarCamera.visibility = View.GONE
                            Toast.makeText(this, "Terjadi kesalahan saat memproses pendeteksian : ($it)", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        binding.btnNext.setOnClickListener {
            val intent = Intent(this@CameraActivity, ListArticleActivity::class.java)
            intent.putExtra(ListArticleActivity.EXTRA_IMAGE, currentImagePath)
            intent.putExtra(ListArticleActivity.EXTRA_PLASTIC_TYPE, plasticType)
            startActivity(intent)
        }
    }

    private fun createTempFile(context: Context): File {
        val storageDirectory = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("plastic $timeStamp", ".jpg", storageDirectory)
    }

    companion object {
        private const val TIME_STAMP_FORMAT = "dd-MMM-yyyy HH:mm:ss"
    }
}