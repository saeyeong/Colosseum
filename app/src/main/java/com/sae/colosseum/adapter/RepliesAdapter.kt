package com.sae.colosseum.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sae.colosseum.R
import com.sae.colosseum.adapter.holder.RepliesViewHolder
import com.sae.colosseum.model.entity.RepliesEntity
import com.sae.colosseum.view.TopicActivity
import kotlinx.android.synthetic.main.item_reply.view.*

class RepliesAdapter(var context: Context, private val list: ArrayList<RepliesEntity>?) :
    RecyclerView.Adapter<RepliesViewHolder>(), View.OnClickListener {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepliesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_reply, parent, false)
        return RepliesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list?.count() ?: 0
    }

    override fun onBindViewHolder(holder: RepliesViewHolder, position: Int) {
        val glide = Glide.with(context)

        holder.containerView.item_post_wrap.setOnClickListener(this)

        list?.get(position)?.let {
            holder.containerView.nickname.text = it.user.nick_name
            holder.containerView.content.text = it.content
            holder.containerView.num_like.text = it.like_count.toString()
            holder.containerView.num_dislike.text = it.dislike_count.toString()
            holder.containerView.num_reply.text = it.reply_count.toString()
//            glide.load(it.img_url).into(holder.containerView.img_post)
        }
    }

    override fun onClick(v: View?) {
//        val position = Integer.parseInt(v?.postNumber?.text.toString())-1
//        val intent = Intent(context, TopicActivity::class.java)
//        val topicId: Int? = list?.get(position)?.id

//        intent.putExtra("topicId", topicId)
//        context.startActivity(intent)
    }
}