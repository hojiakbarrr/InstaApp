package com.example.instaapp.main.add

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.*
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.instaapp.api.RetrofitBuilder
import com.example.instaapp.databinding.FragmentAddPostBinding
import com.example.instaapp.model.AddPostRequest
import com.example.instaapp.model.AddPostResponse
import com.example.instaapp.model.Image
import com.example.instaapp.utils.toImage
import com.example.instaapp.utils.toast
import com.parse.ParseFile
import com.parse.SaveCallback
import okio.IOException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.util.*


class AddPostFragment : Fragment(), LocationListener {
    private val binding: FragmentAddPostBinding by lazy {
        FragmentAddPostBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    lateinit var ferences: SharedPreferences
    var imageUri: Uri? = null
    var selectedBitmap: Bitmap? = null
    var locationManager: LocationManager? = null
    var currentAddress: String? = null
    var age: String? = null
    var imageFile : ParseFile ? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ferences = this.requireActivity().getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = ferences.edit()
        val age = ferences.getString("AGE", "")

        binding.pickImageBtn.setOnClickListener {
            openGallery()
        }
        binding.pickLocatBtn.setOnClickListener {
            getMyLocation()
        }
        binding.creatBtn.setOnClickListener {
            createPost()
        }
    }

    private fun createPost() {
        binding.apply {





            val postRequest = AddPostRequest()
            postRequest.location = currentAddress.toString()
            postRequest.title = editPostTitle.text.toString().trim()
            postRequest.description = editPostDesc.text.toString().trim()
            postRequest.userId = age.toString()
            postRequest.image = imageFile?.toImage()
            Log.d("imageee",postRequest.image?.url.toString())



            val response = RetrofitBuilder.api.createPost(postRequest)
            response.enqueue(object : Callback<AddPostResponse> {
                override fun onResponse(
                    call: Call<AddPostResponse>,
                    response: Response<AddPostResponse>
                ) {
                    if (response.isSuccessful) {
                        toast("Post is created")
                        editPostTitle.text.clear()
                        editPostDesc.text.clear()
                        postImage.setImageResource(0)
                        locationTxt.text = ""
                    }
                }

                override fun onFailure(call: Call<AddPostResponse>, t: Throwable) {
                    toast("Post is not created")
                    Log.d("Post", t.message.toString())
                }

            })

        }


        /* по парсу отправка
         val stream = ByteArrayOutputStream()
        selectedBitmap?.compress(Bitmap.CompressFormat.PNG,100,stream)
        val byteArray = stream.toByteArray()
        val parseFile = ParseFile("postImage.png",byteArray)
        parseFile.saveInBackground(SaveCallback {e->
            if (e == null){
                toast("image save")
            }
        })
        val `object` = ParseObject("Posts")
        `object`.put("title", binding.editPostTitle.text.toString().trim())
        `object`.put("description", binding.editPostDesc.text.toString().trim())
        `object`.put("location", currentAddress.toString())
        `object`.put("image", parseFile)
        `object`.put("userId", age.toString())
        `object`.saveInBackground { e ->
            if (e == null) {
                Toast.makeText(activity, "Post is saved!", Toast.LENGTH_SHORT).show()
                binding.apply {
                    editPostTitle.text.clear()
                    editPostDesc.text.clear()
                    postImage.setImageResource(0)
                    locationTxt.text = ""
                }
            } else {
                Log.i("error", "" + e.message)
            }
        }
         */

    }

    private fun getMyLocation() {
        try {
            locationManager =
                requireActivity().applicationContext.getSystemService(LOCATION_SERVICE) as LocationManager
            if (ActivityCompat.checkSelfPermission(
                    requireActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    1
                )
                return
            }
            locationManager?.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this)
        } catch (e: java.lang.Exception) {
            e.message
        }
    }

    private fun openGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            imageUri = data.data
            try {
                selectedBitmap =
                    MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, imageUri)
                binding.apply {
                    postImage.visibility = View.VISIBLE
                    postImage.setImageBitmap(selectedBitmap)
                }
            } catch (e: Exception) {
                Log.d("error", "" + e.message)
            }


            uploadImage()

        }
    }


    private fun uploadImage() {
        val stream = ByteArrayOutputStream()
        selectedBitmap?.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val byteArray = stream.toByteArray()
        val parseFile = ParseFile("postImage.png", byteArray)
        parseFile.saveInBackground(SaveCallback { e ->
            if (e == null) {
                toast("image save")
                imageFile = parseFile
            }
        })
    }

    override fun onLocationChanged(location: Location) {
        toast(location.latitude.toString() + "___" + location.longitude.toString())
        val geocoder = Geocoder(requireActivity(), Locale.getDefault())
        try {
            val addressList: List<Address> =
                geocoder.getFromLocation(location.latitude, location.longitude, 1)
            currentAddress = addressList[0].getAddressLine(0)
            Log.i("location", currentAddress.toString())
            binding.locationTxt.text = currentAddress
        } catch (e: IOException) {
            e.message
        }
    }


}