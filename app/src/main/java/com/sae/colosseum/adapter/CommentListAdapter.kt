package com.sae.colosseum.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sae.colosseum.R
import com.sae.colosseum.adapter.holder.CommentListViewHolder
import com.sae.colosseum.model.entity.CommentListEntity
import kotlinx.android.synthetic.main.item_reply.view.*

class CommentListAdapter(private val list: List<CommentListEntity>?) : RecyclerView.Adapter<CommentListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_reply, parent, false)
        return CommentListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list?.count()?:0
    }

    override fun onBindViewHolder(holder: CommentListViewHolder, position: Int) {
        list?.get(position)?.let {
            holder.containerView.nickname.text = it.cmtNickname
            holder.containerView.txt_date.text = it.txtDateCmt
            holder.containerView.num_like.text = it.numLike
            holder.containerView.num_dislike.text = it.numDislike
            holder.containerView.content.text = it.txtCmt
        }
    }
}