package com.example.instaapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.instaapp.main.add.AddPostFragment
import com.example.instaapp.main.home.HomeFragment
import com.example.instaapp.main.profil.ProfileFragment

class PagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position){
            0 -> HomeFragment()
            1 -> AddPostFragment()
            2 -> ProfileFragment()
            else -> Fragment()
        }
    }


}