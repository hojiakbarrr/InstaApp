package com.example.instaapp.main.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instaapp.adapters.PostAdapter
import com.example.instaapp.api.RetrofitBuilder
import com.example.instaapp.databinding.FragmentHomeBlankBinding
import com.example.instaapp.model.Post
import com.example.instaapp.model.PostResponse
import com.example.instaapp.utils.toImage
import com.example.instaapp.utils.toast
import com.parse.ParseObject
import com.parse.ParseQuery
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


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
        binding.swipeLayout.setOnRefreshListener {
//                        loadPost()
            getRetrofitPost()
            binding.swipeLayout.isRefreshing = false
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getRetrofitPost()
//        loadPost()
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

                        post.image = `object`.getParseFile("image")?.toImage()

                        /*до ретрофита
                          val file = `object`.getParseFile("image")
                        post.imageUrl = file!!.url
                         */

                        postList.add(post)
                    }
                    adapter = PostAdapter(postList, requireContext())
                    binding.recHome.adapter = adapter

                }
            }
        }

    }

    private fun getRetrofitPost() {
        binding.recHome.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = AdapterPost
            postList.clear()
            val response = RetrofitBuilder.api.getAllPosts()
            response.enqueue(object : Callback<PostResponse> {
                @SuppressLint("NotifyDataSetChanged")
                override fun onResponse(
                    call: Call<PostResponse>,
                    response: Response<PostResponse>
                ) {
                    if (response.isSuccessful) {
                        postList = response.body()?.results as ArrayList<Post>
                        adapter = PostAdapter(postList,requireContext())
                        adapter?.notifyDataSetChanged()

                    }
                }

                override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                    toast("проблемы с подкгрузкой")
                }

            })
            binding.swipeLayout.isRefreshing = false
        }

    }

}