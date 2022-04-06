package com.example.instaapp.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instaapp.adapters.PostAdapter
import com.example.instaapp.databinding.FragmentHomeBlankBinding
import com.example.instaapp.model.Post
import com.parse.ParseObject
import com.parse.ParseQuery


class HomeFragment : Fragment() {
    private val binding: FragmentHomeBlankBinding by lazy {
        FragmentHomeBlankBinding.inflate(layoutInflater)
    }
    private lateinit var AdapterPost: PostAdapter
    var postList: ArrayList<Post> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AdapterPost = PostAdapter(postList, requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        preparRec()
        loadPost()
    }

    private fun loadPost() {
        binding.recHome.apply {
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
            adapter = AdapterPost
            postList.clear()
            val query = ParseQuery.getQuery<ParseObject>("Posts")
            query.findInBackground { objects, e ->
                if (objects!!.size > 0 && e == null) {
                    for (`object` in objects) {
                        val post = Post()
                        post.description = (`object`!!.getString("description"))
                        post.title = (`object`.getString("title"))
                        post.location = (`object`.getString("location"))
                        val file = `object`.getParseFile("image")
                        post.imageUrl = file!!.url
                        postList.add(post)
                    }
                    adapter = PostAdapter(postList, requireContext())
                    binding.recHome.adapter = adapter
                    binding.swipeLayout.setOnRefreshListener {
                        loadPost()
                        adapter?.notifyDataSetChanged()
                        binding.swipeLayout.isRefreshing = false
                    }
                }
            }
        }

    }

    private fun preparRec() {
        binding.recHome.apply {
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
            adapter = AdapterPost
        }
    }



}