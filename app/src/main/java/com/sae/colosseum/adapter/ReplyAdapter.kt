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
import com.sae.colosseum.utils.GlobalApplication
import com.sae.colosseum.utils.ResultInterface
import com.sae.colosseum.view.TopicActivity
import kotlinx.android.synthetic.main.item_reply.view.*
import java.util.*

class ReplyAdapter(
    private val list: ArrayList<RepliesEntity>?,
    private val mCallback: RecyclerViewListener<RepliesEntity, View>
) : RecyclerView.Adapter<RepliesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepliesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_reply, parent, false)
        val holder = RepliesViewHolder(view)
        var position: Int
        var item: RepliesEntity?

        View.OnClickListener {
            position = holder.adapterPosition
            item = list?.get(position)
            item?.run {
                mCallback.onClickItemForViewId(this, it, view)
            }
        }.run {
            // 좋아요/싫어요 클릭 리스너
            holder.itemView.like_wrap.setOnClickListener(this)
            holder.itemView.dislike_wrap.setOnClickListener(this)
            holder.itemView.btn_menu.setOnClickListener(this)
        }

        return holder
    }

    override fun getItemCount(): Int {
        return list?.count() ?: 0
    }

    override fun onBindViewHolder(holder: RepliesViewHolder, position: Int) {
        list?.get(position)?.let {
            holder.containerView.nickname.text = it.user.nick_name
            holder.containerView.content.text = it.content
            holder.containerView.num_like.text = it.like_count.toString()
            holder.containerView.num_dislike.text = it.dislike_count.toString()
            holder.containerView.num_reply.text = it.reply_count.toString()

//            내 댓글만 수정 메뉴 보임
            if (it.user.id == GlobalApplication.loginUser.id) {
                holder.containerView.btn_menu.visibility = VISIBLE
            }

//            좋아요/싫어요 상태
            if (it.my_like) {
                holder.containerView.img_like.setImageResource(R.drawable.like_on)
                holder.containerView.img_dislike.setImageResource(R.drawable.like_off)
            } else if (it.my_dislike) {
                holder.containerView.img_like.setImageResource(R.drawable.like_off)
                holder.containerView.img_dislike.setImageResource(R.drawable.dislike_on)
            } else {
                holder.containerView.img_like.setImageResource(R.drawable.like_off)
                holder.containerView.img_dislike.setImageResource(R.drawable.dislike_off)
            }
        }
    }
}