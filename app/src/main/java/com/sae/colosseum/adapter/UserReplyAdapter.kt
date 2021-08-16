package com.sae.colosseum.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sae.colosseum.R
import com.sae.colosseum.model.entity.ReplyEntity
import kotlinx.android.synthetic.main.item_user_reply.view.*

open class UserReplyAdapter(
    var list: ArrayList<ReplyEntity>?
) : RecyclerView.Adapter<UserReplyAdapter.UserReplyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserReplyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user_reply, parent, false)
        val holder = UserReplyViewHolder(view)

        return holder
    }

    override fun getItemCount(): Int {
        return list?.count() ?: 0
    }

    class UserReplyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onBindViewHolder(holder: UserReplyViewHolder, position: Int) {
        list?.get(position)?.let {
            holder.itemView.run {
                content.text = it.content
                txt_date.text = it.updated_at
                txt_topic.text = it.project_info.title
            }
        }
    }
}