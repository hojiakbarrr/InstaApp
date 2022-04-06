package com.example.instaapp.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.instaapp.R
import com.example.instaapp.adapters.PagerAdapter
import com.example.instaapp.databinding.ActivityMainBinding
import com.example.instaapp.model.Post
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.parse.*
import java.util.*


class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initial()

        /* получение его по имени и по его айди
        val query = ParseQuery.getQuery<ParseObject>("SoccerPlayers")
        query.whereEqualTo("playerName", "A. Wed")
        query.whereEqualTo("objectId", "GXfgPQtjWF")
        query.getFirstInBackground(GetCallback<ParseObject>() { `object`, e ->
            if (e == null) {
                val playerName = `object`.getInt("yearOfBirth")
                val email = `object`.getString("emailContact")
                Log.d("MainActivity22", "${playerName}*-*${email}")
                // object will be your game score
            } else {
                Log.d("MainActivity", "fail")
                // something went wrong
            }
        })
         */
        ///////////////////////////////////////////////////////////////////////////////////////
        /* получение объекта по его айдишке
        val query = ParseQuery.getQuery<ParseObject>("SoccerPlayers")
        query.getInBackground("GXfgPQtjWF") { `object`, e ->
            if (e == null) {
                val playerName = `object`.getInt("yearOfBirth")
                Log.d("MainActivity", "${playerName}")
                // object will be your game score
            } else {
                Log.d("MainActivity", "fail")
                // something went wrong
            }
        }
        * */


        /////////////////////////////////////////////////////////////////////////////////////////

        /*создание игрока внутри класса и самого класса
        // Configure Query

        */
//        val soccerPlayers = ParseObject("SoccerPlayers")
//        // Store an object
//        soccerPlayers.put("playerName", "A. Wed")
//        soccerPlayers.put("yearOfBirth", 1997)
//        soccerPlayers.put("emailContact", "a.wed@email.io")
//        soccerPlayers.addAllUnique("attributes", Arrays.asList("fast", "good conditioning"))
//        // Saving object
//        soccerPlayers.saveInBackground(object : SaveCallback {
//            override fun done(e: ParseException?) {
//                if (e == null) {
//                    // Success
//                } else {
//                    // Error
//                }
//            }
//        })

/////////////////////////////////////////////////////////////////////////////////////////////////////
        /*изменение или обновление
         val query = ParseQuery.getQuery<ParseObject>("SoccerPlayers")
        // Retrieve the object by id
        query.getInBackground("HMcTr9rD3s") { player, e ->
            if (e == null) {
                // Now let's update it with some new data. In this case, only cheatMode and score
                // will get sent to the Parse Cloud. playerName hasn't changed.
                player.put("yearOfBirth", 1998)
                player.put("emailContact", "a.wed@domain.io")
                player.saveInBackground()
            } else {
                // Failed
            }
        }
         */
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /*работает и удаление по имени и айдишке
        val query = ParseQuery.getQuery<ParseObject>("SoccerPlayers")
        query.whereEqualTo("playerName", "A. Wed")
        query.whereEqualTo("objectId", "GXfgPQtjWF")
        query.findInBackground(FindCallback<ParseObject>() { `object`, e ->
            if (e == null){
                `object`.get(0).deleteInBackground(DeleteCallback {
                    if (e == null) {
                        Log.d("MainActivity22", "is deleted")
                        // object will be your game score
                    } else {
                        Log.d("MainActivity22", "внутренний")
                        // something went wrong
                    }
                })
            }else{
                Log.d("MainActivity22", "внешний")
            }

        })
         */


        setContentView(binding.root)
    }


    private fun initial() {
        binding.viewPager.adapter = PagerAdapter(supportFragmentManager, lifecycle)
        binding.tabLayout.tabIconTint = null

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.setIcon(R.drawable.ic_home)
                    tab.text = "Home"
                    tab.icon?.setTint(ContextCompat.getColor(this, R.color.red))

                }
                1 -> {
                    tab.setIcon(R.drawable.ic_addbox)
                    tab.text = "Add post"
                    tab.icon?.setTint(ContextCompat.getColor(this, R.color.red))
                    tab.icon!!.alpha = 70
                }
                2 -> {
                    tab.setIcon(R.drawable.ic_person)
                    tab.text = "Profile"
                    tab.icon?.setTint(ContextCompat.getColor(this, R.color.red))
                    tab.icon!!.alpha = 70
                }
            }
        }.attach()

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab!!.icon!!.alpha = 250
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab!!.icon!!.alpha = 70
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                tab?.icon!!.alpha = 250

            }

        })
    }
}



