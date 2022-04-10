package com.example.instaapp.utils

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.instaapp.model.Image
import com.parse.ParseFile


fun Fragment.toast(message: String){
    Toast.makeText(requireContext(),message, Toast.LENGTH_SHORT).show()
}

fun Context.toast(message: String){
    Toast.makeText(this,message, Toast.LENGTH_SHORT).show()
}
fun ParseFile.toImage(): Image =
    Image(name = name, type = "File", url = url)