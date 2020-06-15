package com.sae.colosseum.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sae.colosseum.R
import com.sae.colosseum.adapter.holder.ReplyViewHolder
import com.sae.colosseum.interfaces.RecyclerViewListener
import com.sae.colosseum.model.entity.RepliesEntity
import com.sae.colosseum.model.entity.TopicInfoEntity
import com.sae.colosseum.utils.GlobalApplication
import kotlinx.android.synthetic.main.activity_topic.view.*
import kotlinx.android.synthetic.main.item_reply.view.*

class ReplyAdapter(
    private val topicInfoEntity: TopicInfoEntity?,
    private val mCallback: RecyclerViewListener<RepliesEntity, View>
) : RecyclerView.Adapter<ReplyViewHolder>() {

    var upId: Int = topicInfoEntity?.run {this.sides[0].id} ?: -1
    var downId: Int = topicInfoEntity?.run {this.sides[1].id} ?: -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReplyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_reply, parent, false)
        val holder = ReplyViewHolder(view)
        var position: Int
        var item: RepliesEntity?


        View.OnClickListener {
            position = holder.adapterPosition
            item = topicInfoEntity?.replies?.get(position)
            item?.run {
                mCallback.onClickItemForViewId(this, it, view)
            }
        }.run {
            // 좋아요/싫어요 클릭 리스너
            holder.itemView.like_wrap.setOnClickListener(this)
            holder.itemView.dislike_wrap.setOnClickListener(this)
            holder.itemView.btn_menu.setOnClickListener(this)
            holder.itemView.btn_re_reply.setOnClickListener(this)
        }

        return holder
    }

    override fun getItemCount(): Int {
        return topicInfoEntity?.replies?.count() ?: 0
    }

    override fun onBindViewHolder(holder: ReplyViewHolder, position: Int) {
        topicInfoEntity?.replies?.get(position)?.let {
            holder.containerView.nickname.text = it.user?.nick_name
            holder.containerView.content.text = it.content
            holder.containerView.txt_date.text = it.updated_at
            holder.containerView.num_like.text = it.like_count.toString()
            holder.containerView.num_dislike.text = it.dislike_count.toString()
            holder.containerView.num_re_reply.text = it.reply_count.toString()


            if (it.side_id == upId) {
                holder.containerView.userInfo.setBackgroundResource(R.color.colorUp)
                holder.containerView.userInfo.background.alpha = 50
            } else if (it.side_id == downId) {
                holder.containerView.userInfo.setBackgroundResource(R.color.colorDown)
                holder.containerView.userInfo.background.alpha = 50
            }

//            내 댓글만 수정 메뉴 보임
            if (it.user?.id == GlobalApplication.loginUser.id) {
                holder.containerView.btn_menu.visibility = VISIBLE
            }

//            좋아요/싫어요 상태
            if (it.my_like ?: run { false }) {
                holder.containerView.img_like.setImageResource(R.drawable.like_on)
                holder.containerView.img_dislike.setImageResource(R.drawable.like_off)
            } else if (it.my_dislike ?: run { false }) {
                holder.containerView.img_like.setImageResource(R.drawable.like_off)
                holder.containerView.img_dislike.setImageResource(R.drawable.dislike_on)
            } else {
                holder.containerView.img_like.setImageResource(R.drawable.like_off)
                holder.containerView.img_dislike.setImageResource(R.drawable.dislike_off)
            }
        }
    }
}