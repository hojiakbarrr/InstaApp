package com.example.instaapp.main.profil

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.instaapp.R
import com.example.instaapp.databinding.FragmentProfileBinding
import com.example.instaapp.databinding.LogInFragmentBinding
import com.example.instaapp.register.RegisterActivity
import com.example.instaapp.utils.toast
import com.parse.GetCallback
import com.parse.ParseObject
import com.parse.ParseQuery

class ProfileFragment : Fragment() {
    private val binding: FragmentProfileBinding by lazy {
        FragmentProfileBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    lateinit var ferences: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        ferences = this.requireActivity().getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = ferences.edit()
        val age = ferences.getString("AGE","")

        val query = ParseQuery.getQuery<ParseObject>("Accounts")
        query.whereEqualTo("username", age)
        query.getFirstInBackground(GetCallback<ParseObject>() { `object`, e ->
            if (e == null) {
                val playerName = `object`.getString("username")
                val email = `object`.getString("email")
                binding.editNameTxt.setText(playerName)
                binding.editEmailTxt.setText(email)
                toast("Загрузились данные на профиль")
                Log.d("MainActivity22", "${playerName}*-*${email}")
                // object will be your game score
            } else {
                toast("Не загружаются данные на профиль")
                // something went wrong
            }
        })

        binding.logOutBtn.setOnClickListener {
            editor.clear()
            editor.apply()
            startActivity(Intent(requireActivity(),RegisterActivity::class.java))
        }

        // Inflate the layout for this fragment
        return binding.root
    }

}