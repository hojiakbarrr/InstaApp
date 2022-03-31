package com.example.instaapp.register

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.instaapp.R
import com.example.instaapp.databinding.ActivityRegisterBinding
import com.example.instaapp.databinding.LogUpFragmentBinding
import com.example.instaapp.main.MainActivity

class RegisterActivity : AppCompatActivity() {
    private val binding: ActivityRegisterBinding by lazy {
        ActivityRegisterBinding.inflate(layoutInflater)
    }
    lateinit var preferences: SharedPreferences

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        preferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)

        val age = preferences.getInt("AGE",0)
        if (age > 0){
            startActivity(Intent(this,MainActivity::class.java))
        }

    }
}