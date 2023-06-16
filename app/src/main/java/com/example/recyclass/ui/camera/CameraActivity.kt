package com.example.recyclass.ui.camera

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import com.example.recyclass.R
import com.example.recyclass.SettingApplication
import com.example.recyclass.data.Indicator
import com.example.recyclass.databinding.ActivityCameraBinding
import com.example.recyclass.ui.listarticle.ListArticleActivity
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*

class CameraActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCameraBinding
    private lateinit var viewModel: CameraViewModel
    private lateinit var currentImagePath: String
    private lateinit var getImage: File
    private var imageIsReady = 0
    private var plasticType = ""
    private val timeStamp = SimpleDateFormat(
        TIME_STAMP_FORMAT,
        Locale.US
    ).format(System.currentTimeMillis())

    private val launcherCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val image = File(currentImagePath)
            image.let {
                getImage = it
                imageIsReady = 1
                binding.imageView.setImageBitmap(BitmapFactory.decodeFile(it.path))
            }
        }
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val image = result.data?.data as Uri
            image.let {
                getImage = uriToFile(it, this)
                imageIsReady = 1
                currentImagePath = getImage.absolutePath
                binding.imageView.setImageURI(it)
            }
        }
    }

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

        binding.btnGallery.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            launcherGallery.launch(intent)
        }

        binding.btnUpload.setOnClickListener {
            if (imageIsReady == 1) {
                val image = compressImage(getImage)
                val requestImage = image.asRequestBody("image/jpeg".toMediaType())
                val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                    "file",
                    image.name,
                    requestImage
                )
                viewModel.uploadImage(imageMultipart).observe(this) {
                    when (it) {
                        is Indicator.Success -> {
                            if (it.plasticType == "No Plastic Found") {
                                with(binding) {
                                    progressBarCamera.visibility = View.GONE
                                    imageView.setImageResource(R.drawable.placeholder_picture)
                                }
                                imageIsReady = 0
                                Toast.makeText(this, getString(R.string.no_plastic_match), Toast.LENGTH_SHORT).show()
                            }
                            else {
                                with(binding) {
                                    progressBarCamera.visibility = View.GONE
                                    btnNext.isEnabled = true
                                    btnCamera.isEnabled = false
                                    btnGallery.isEnabled = false
                                    btnUpload.isEnabled = false
                                }
                                plasticType = it.plasticType
                                Toast.makeText(this, getString(R.string.success_to_detect), Toast.LENGTH_SHORT).show()
                            }
                        }
                        is Indicator.Loading -> {
                            binding.progressBarCamera.visibility = View.VISIBLE
                        }
                        is Indicator.Error -> {
                            binding.progressBarCamera.visibility = View.GONE
                            Toast.makeText(this, getString(R.string.failed_to_detect, it), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
            else {
                Toast.makeText(this, getString(R.string.insert_image_first), Toast.LENGTH_SHORT).show()
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

    private fun uriToFile(uri: Uri, context: Context): File {
        val contentResolver = context.contentResolver
        val file = createTempFile(context)

        val inputStream: InputStream = contentResolver.openInputStream(uri) as InputStream
        val outputStream: OutputStream = FileOutputStream(file)

        val bufferSize = ByteArray(1024)
        var temp: Int
        while (inputStream.read(bufferSize).also { temp = it } > 0) {
            outputStream.write(bufferSize, 0, temp)
        }
        inputStream.close()
        outputStream.close()

        return file
    }

    private fun compressImage(image: File) : File {
        var quality = 100
        var streamLen: Int
        val bitmapImage = BitmapFactory.decodeFile(image.path)

        do {
            val stream = ByteArrayOutputStream()
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, quality, stream)
            val byteArray = stream.toByteArray()
            streamLen = byteArray.size
            quality -= 3
        }
        while (streamLen > 10000000)

        bitmapImage.compress(Bitmap.CompressFormat.JPEG, quality, FileOutputStream(image))
        return image
    }

    companion object {
        private const val TIME_STAMP_FORMAT = "dd-MMM-yyyy HH:mm:ss"
    }
}