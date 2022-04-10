package com.example.instaapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.instaapp.R
import com.example.instaapp.model.Post
import com.squareup.picasso.Picasso


class PostAdapter(var postList: List<Post>, var context: Context) :
    RecyclerView.Adapter<PostAdapter.Holder>() {
    var listener: RecycleOnClickListener? = null

    interface RecycleOnClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: RecycleOnClickListener?) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.post_cardview , parent, false)
        return Holder(view, listener)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.card_location.text = postList[position].location
        holder.card_description.text = postList[position].description
        holder.card_title.text = postList[position].title
        Picasso.get().load(postList[position].image?.url).into(holder.card_image)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    class Holder(itemView: View, listener: RecycleOnClickListener?) :
        RecyclerView.ViewHolder(itemView) {
        var card_location: TextView
        var card_title: TextView
        var card_description: TextView
        var card_image: ImageView

        init {
            card_location = itemView.findViewById(R.id.card_location)
            card_title = itemView.findViewById(R.id.card_title)
            card_description = itemView.findViewById(R.id.card_description)
            card_image = itemView.findViewById(R.id.card_image)

//            itemView.setOnClickListener { view ->
//                val position = adapterPosition
//                listener!!.onItemClick(position)
//            }
        }
    }

}