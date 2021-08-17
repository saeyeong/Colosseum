package com.sae.colosseum.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sae.colosseum.R
import com.sae.colosseum.databinding.ItemTopicBinding
import com.sae.colosseum.databinding.ItemUserReplyBinding
import com.sae.colosseum.model.entity.ReplyEntity
import com.sae.colosseum.model.entity.TopicInfoEntity
import com.sae.colosseum.utils.GlobalApplication

open class UserReplyAdapter(
    var list: ArrayList<ReplyEntity>?
) : RecyclerView.Adapter<UserReplyAdapter.ViewHolder>() {

    class ViewHolder(
        val viewBinding: ItemUserReplyBinding
    ) : RecyclerView.ViewHolder(
        viewBinding.root
    ) {

        fun bindViewHolder(item: ReplyEntity) {
            viewBinding.content.text = item.content
            viewBinding.txtDate.text = item.updated_at
            viewBinding.txtTopic.text = item.project_info.title

            viewBinding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemUserReplyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = ViewHolder(view)

        return holder
    }

    override fun getItemCount(): Int {
        return list?.count() ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        list?.get(position)?.let {
            holder.bindViewHolder(it)
        }
    }
}