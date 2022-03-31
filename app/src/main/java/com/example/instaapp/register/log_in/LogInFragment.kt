package com.example.instaapp.register.log_in

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.instaapp.R
import com.example.instaapp.databinding.LogInFragmentBinding
import com.example.instaapp.main.MainActivity
import com.example.instaapp.utils.toast
import com.parse.GetCallback
import com.parse.ParseObject
import com.parse.ParseQuery
import java.util.prefs.AbstractPreferences

class LogInFragment : Fragment() {

    companion object {
        fun newInstance() = LogInFragment()
    }

    private lateinit var viewModel: LogInViewModel
    private val binding: LogInFragmentBinding by lazy {
        LogInFragmentBinding.inflate(layoutInflater)
    }
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        sharedPreferences = this.requireActivity().getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = sharedPreferences.edit()


        binding.signUpBtn.setOnClickListener {
            Navigation.findNavController(requireView())
                .navigate(R.id.from_logInFragment_to_logUpFragment)
        }

        binding.logInBtn.setOnClickListener {
            var userName = binding.logInName.text
            var password = binding.logInPassword.text
            val query = ParseQuery.getQuery<ParseObject>("Accounts")
            query.whereEqualTo("username", userName.toString().trim())
            query.whereEqualTo("password", password.toString().trim())
            query.getFirstInBackground(GetCallback<ParseObject>() { `object`, e ->
                if (e == null) {

                    toast("C возвращением")
                    editor.putInt("AGE",10)
                    editor.apply()
                    val intent = Intent(requireContext(), MainActivity::class.java)
                    startActivity(intent)

                    // object will be your game score
                } else {
                    Log.d("MainActivity", "fail")
                    toast("Аккаунт не подтвержден")
                    // something went wrong
                }
            })
        }


        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LogInViewModel::class.java)
        // TODO: Use the ViewModel
    }

}