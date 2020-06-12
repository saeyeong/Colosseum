package com.sae.colosseum.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sae.colosseum.R
import com.sae.colosseum.adapter.holder.ReplyViewHolder
import com.sae.colosseum.interfaces.RecyclerViewListener
import com.sae.colosseum.model.entity.RepliesEntity
import com.sae.colosseum.utils.GlobalApplication
import kotlinx.android.synthetic.main.item_reply.view.*
import java.util.*

class ReReplyAdapter(
    private val list: ArrayList<RepliesEntity>?) : RecyclerView.Adapter<ReplyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReplyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_re_reply, parent, false)
        val holder = ReplyViewHolder(view)

        return holder
    }

    override fun getItemCount(): Int {
        return list?.count() ?: 0
    }

    override fun onBindViewHolder(holder: ReplyViewHolder, position: Int) {
        list?.get(position)?.let {
            holder.containerView.nickname.text = it.user?.nick_name
            holder.containerView.content.text = it.content
        }
    }
}