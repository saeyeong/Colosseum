package com.sae.coloseum.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.sae.coloseum.R
import com.sae.coloseum.adapter.holder.PostListViewHolder
import com.sae.coloseum.model.entity.PostListEntity
import kotlinx.android.synthetic.main.item_post.view.*

class PostListAdapter(val list: List<PostListEntity>?) : RecyclerView.Adapter<PostListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return PostListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list?.count()?:0
    }

    override fun onBindViewHolder(holder: PostListViewHolder, position: Int) {
        list?.get(position)?.let {
            holder.containerView.postNumber.text = it.postNumber
            holder.containerView.postTitle.text = it.postTitle
            holder.containerView.agreeCount.text = it.agreeCount
            holder.containerView.disagreeCount.text = it.disagreeCount
            holder.containerView.commentCount.text = it.commentCount
            holder.containerView.Writer.text = it.Writer
        }
    }
}