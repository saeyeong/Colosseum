package com.sae.colosseum.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sae.colosseum.R
import com.sae.colosseum.adapter.holder.RepliesViewHolder
import com.sae.colosseum.interfaces.RecyclerViewListener
import com.sae.colosseum.model.entity.RepliesEntity
import com.sae.colosseum.view.TopicActivity
import kotlinx.android.synthetic.main.item_reply.view.*
import java.util.*

class RepliesAdapter(var context: Context, private val list: ArrayList<RepliesEntity>?, private val mCallback: RecyclerViewListener) :
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


        list?.get(position)?.let {
            val item = it
            holder.containerView.nickname.text = it.user.nick_name
            holder.containerView.content.text = it.content
            holder.containerView.num_like.text = it.like_count.toString()
            holder.containerView.num_dislike.text = it.dislike_count.toString()
            holder.containerView.num_reply.text = it.reply_count.toString()
            holder.containerView.nickname.tag = it.id.toString()
            holder.containerView.like_wrap.setOnClickListener {
                mCallback.onClickItemForViewId(position, item, R.id.like_wrap)
            }
            holder.containerView.dislike_wrap.setOnClickListener {
                mCallback.onClickItemForViewId(position, item, R.id.dislike_wrap)
            }


            Log.d("test",it.my_like.toString())

            if(it.my_like) {
                holder.containerView.img_like.setImageResource(R.drawable.like_on)
            } else if (it.my_dislike) {
                holder.containerView.img_dislike.setImageResource(R.drawable.dislike_on)
            }
//            glide.load(it.img_url).into(holder.containerView.img_post)
        }
    }

    override fun onClick(v: View?) {

    }
}