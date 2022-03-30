package com.example.instaapp.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.instaapp.R
import com.example.instaapp.databinding.ActivityRegisterBinding
import com.example.instaapp.databinding.LogUpFragmentBinding

class RegisterActivity : AppCompatActivity() {
    private val binding: ActivityRegisterBinding by lazy {
        ActivityRegisterBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}