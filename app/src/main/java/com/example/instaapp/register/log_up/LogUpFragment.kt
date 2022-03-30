package com.example.instaapp.register.log_up

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.instaapp.R
import com.example.instaapp.databinding.LogInFragmentBinding
import com.example.instaapp.databinding.LogUpFragmentBinding

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


        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LogUpViewModel::class.java)
        // TODO: Use the ViewModel
    }

}