package com.sae.colosseum.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.sae.colosseum.R
import com.sae.colosseum.adapter.holder.ListTopicViewHolder
import com.sae.colosseum.interfaces.RecyclerViewListener
import com.sae.colosseum.model.entity.TopicInfoEntity
import kotlinx.android.synthetic.main.item_topic.view.*

open class ListTopicAdapter(
    private var glide: RequestManager?,
    private val list: ArrayList<TopicInfoEntity>?,
    private val mCallback: RecyclerViewListener<TopicInfoEntity, View>
) : RecyclerView.Adapter<ListTopicViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListTopicViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_topic, parent, false)
        val holder = ListTopicViewHolder(view)
        var position: Int
        var item: TopicInfoEntity?

        holder.itemView.setOnClickListener {
            position = holder.adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                item = list?.get(position)
                item?.run {
                    mCallback.onClickItem(this, it, view)
                }
            }
        }

        return holder
    }

    override fun getItemCount(): Int {
        return list?.count() ?: 0
    }

    override fun onBindViewHolder(holder: ListTopicViewHolder, position: Int) {
        list?.get(position)?.let {
            holder.itemView.topic_number.text = (position + 1).toString()
            holder.itemView.topic_title.text = it.title
            holder.itemView.num_agree.text = it.sides[0].vote_count.toString()
            holder.itemView.num_disagree.text = it.sides[1].vote_count.toString()
            holder.itemView.end_date.text = it.end_date
            holder.itemView.num_reply.text = it.reply_count.toString()
            glide?.load(it.img_url)?.into(holder.itemView.img_topic)
        }
    }
}