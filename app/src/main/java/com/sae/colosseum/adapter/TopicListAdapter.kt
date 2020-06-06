package com.sae.colosseum.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sae.colosseum.R
import com.sae.colosseum.adapter.holder.TopicListViewHolder
import com.sae.colosseum.model.entity.TopicInfoEntity
import com.sae.colosseum.view.TopicActivity
import kotlinx.android.synthetic.main.item_post.view.*

class TopicListAdapter(var context: Context, private val list: List<TopicInfoEntity>?) :
    RecyclerView.Adapter<TopicListViewHolder>(), View.OnClickListener {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return TopicListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list?.count() ?: 0
    }

    override fun onBindViewHolder(holder: TopicListViewHolder, position: Int) {
        val glide = Glide.with(context)

        holder.containerView.item_post_wrap.setOnClickListener(this)

        list?.get(position)?.let {
            holder.containerView.postNumber.text = (position + 1).toString()
            holder.containerView.postTitle.text = it.title
            holder.containerView.agreeCount.text = it.sides[0].vote_count.toString()
            holder.containerView.disagreeCount.text = it.sides[1].vote_count.toString()
            holder.containerView.end_date.text = it.end_date
            holder.containerView.commentCount.text = it.reply_count.toString()
            glide.load(it.img_url).into(holder.containerView.img_post)
        }
    }

    override fun onClick(v: View?) {
        val position = Integer.parseInt(v?.postNumber?.text.toString())-1
        val intent = Intent(context, TopicActivity::class.java)
        val topicId: Int? = list?.get(position)?.id

        intent.putExtra("topicId", topicId)
        context.startActivity(intent)
    }
}