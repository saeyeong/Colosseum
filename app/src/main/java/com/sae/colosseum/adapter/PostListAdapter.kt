package com.sae.colosseum.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sae.colosseum.R
import com.sae.colosseum.adapter.holder.PostListViewHolder
import com.sae.colosseum.model.entity.Topic
import kotlinx.android.synthetic.main.item_post.view.*

class PostListAdapter(context: Context, private val list: List<Topic>?) : RecyclerView.Adapter<PostListViewHolder>() {

    val glide = Glide.with(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return PostListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list?.count() ?: 0
    }

    override fun onBindViewHolder(holder: PostListViewHolder, position: Int) {
        list?.get(position)?.let {
            holder.containerView.postNumber.text = (position+1).toString()
            holder.containerView.postTitle.text = it.title
            holder.containerView.agreeCount.text = it.sides[0].vote_count.toString()
            holder.containerView.disagreeCount.text = it.sides[1].vote_count.toString()
            holder.containerView.end_date.text = it.end_date
            glide.load(it.img_url).into(holder.containerView.img_post)
        }
    }
}