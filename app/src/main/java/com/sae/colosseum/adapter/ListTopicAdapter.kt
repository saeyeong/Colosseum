package com.sae.colosseum.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.sae.colosseum.R
import com.sae.colosseum.adapter.holder.ListTopicViewHolder
import com.sae.colosseum.interfaces.RecyclerViewListener
import com.sae.colosseum.model.entity.TopicInfoEntity
import kotlinx.android.synthetic.main.item_topic.view.*

open class ListTopicAdapter(
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
            holder.itemView.run {
                topic_number.text = (position + 1).toString()
                topic_title.text = it.title
                num_agree.text = it.sides[0].vote_count.toString()
                num_disagree.text = it.sides[1].vote_count.toString()
                end_date.text = it.end_date
                num_reply.text = it.reply_count.toString()
                Glide.with(img_topic.context).load(it.img_url)?.into(img_topic)
            }
        }
    }
}