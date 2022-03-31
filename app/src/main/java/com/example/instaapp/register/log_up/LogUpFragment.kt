package com.example.instaapp.register.log_up

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.instaapp.R
import com.example.instaapp.databinding.LogUpFragmentBinding
import com.example.instaapp.utils.toast
import com.parse.ParseException
import com.parse.ParseObject
import com.parse.SaveCallback
import java.util.*

class LogUpFragment : Fragment() {

    companion object {
        fun newInstance() = LogUpFragment()
    }

    private lateinit var viewModel: LogUpViewModel
    private val binding: LogUpFragmentBinding by lazy {
        LogUpFragmentBinding.inflate(layoutInflater)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding.signInBtn.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.from_logUpFragment_to_logInFragment)
        }

        binding.btnSignUp.setOnClickListener {
            val userName = binding.logInName.text
            val email = binding.textEmail.text
            val password = binding.logInPassword.text


            val addnewuser = ParseObject("Accounts")
            // Store an object
            addnewuser.put("username", userName.toString().trim())
            addnewuser.put("email", email.toString().trim())
            addnewuser.put("password", password.toString().trim())
//            addnewuser.addAllUnique("attributes", Arrays.asList("fast", "good conditioning"))
            // Saving object
            addnewuser.saveInBackground(object : SaveCallback {
                override fun done(e: ParseException?) {
                    if (e == null) {
                        toast("вы успешно прошли регистрацию")
                        Navigation.findNavController(requireView()).navigate(R.id.from_logUpFragment_to_logInFragment)
                        // Success
                    } else {
                        toast("не смогли вас зарегистрировать")
                        // Error
                    }
                }
            })



        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LogUpViewModel::class.java)
        // TODO: Use the ViewModel
    }

}