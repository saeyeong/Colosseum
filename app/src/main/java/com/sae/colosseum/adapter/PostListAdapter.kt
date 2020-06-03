package com.sae.colosseum.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sae.colosseum.R
import com.sae.colosseum.adapter.holder.PostListViewHolder
import com.sae.colosseum.model.entity.Topic
import kotlinx.android.synthetic.main.item_post.view.*

class PostListAdapter(private val list: List<Topic>?) : RecyclerView.Adapter<PostListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return PostListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list?.count() ?: 0
    }

    override fun onBindViewHolder(holder: PostListViewHolder, position: Int) {
        list?.get(position)?.let {
            holder.containerView.postTitle.text = it.title
            holder.containerView.agreeCount.text = it.sides[0].vote_count.toString()
            holder.containerView.disagreeCount.text = it.sides[1].vote_count.toString()
            holder.containerView.end_date.text = it.end_date
        }
    }
}