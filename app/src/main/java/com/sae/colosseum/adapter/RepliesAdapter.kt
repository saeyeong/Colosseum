package com.sae.colosseum.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sae.colosseum.R
import com.sae.colosseum.adapter.holder.RepliesViewHolder
import com.sae.colosseum.interfaces.RecyclerViewListener
import com.sae.colosseum.model.entity.DataEntity
import com.sae.colosseum.model.entity.RepliesEntity
import com.sae.colosseum.network.ServerClient
import com.sae.colosseum.utils.ResultInterface
import com.sae.colosseum.view.TopicActivity
import kotlinx.android.synthetic.main.item_reply.view.*
import java.util.*

class RepliesAdapter(
    var context: Context,
    private val list: ArrayList<RepliesEntity>,
    private val mCallback: RecyclerViewListener<RepliesEntity, View>
) : RecyclerView.Adapter<RepliesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepliesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_reply, parent, false)
        val holder = RepliesViewHolder(view)
        var position: Int
        var item: RepliesEntity

        View.OnClickListener {
            position = holder.adapterPosition
            item = list[position]
            mCallback.onClickItemForViewId(item, it, view)
        }.run {
            // 좋아요/싫어요 클릭 리스너
            holder.containerView.like_wrap.setOnClickListener(this)
            holder.containerView.dislike_wrap.setOnClickListener(this)
            holder.containerView.btn_menu.setOnClickListener(this)
        }

        return holder
    }

    override fun getItemCount(): Int {
        return list.count() ?: 0
    }

    override fun onBindViewHolder(holder: RepliesViewHolder, position: Int) {
//        val glide = Glide.with(context)

        list[position].let {
            holder.containerView.nickname.text = it.user.nick_name
            holder.containerView.content.text = it.content
            holder.containerView.num_like.text = it.like_count.toString()
            holder.containerView.num_dislike.text = it.dislike_count.toString()
            holder.containerView.num_reply.text = it.reply_count.toString()

//            좋아요/싫어요 상태 바인딩
            if (it.my_like) { // my_like 가 true 라면
                holder.containerView.img_like.setImageResource(R.drawable.like_on)
            } else if (it.my_dislike) { // my_dislike 가 true 라면
                holder.containerView.img_dislike.setImageResource(R.drawable.dislike_on)
            } else { // 둘다 false 라면
                holder.containerView.img_like.setImageResource(R.drawable.like_off)
                holder.containerView.img_dislike.setImageResource(R.drawable.dislike_off)
            }

//            glide.load(it.img_url).into(holder.containerView.img_post)
        }
    }
}