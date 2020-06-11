package com.sae.colosseum.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.sae.colosseum.R
import com.sae.colosseum.adapter.holder.TopicListViewHolder
import com.sae.colosseum.interfaces.RecyclerViewListener
import com.sae.colosseum.model.entity.TopicInfoEntity
import kotlinx.android.synthetic.main.item_topic.view.*

open class TopicListAdapter(
    private var glide: RequestManager?,
    private val list: ArrayList<TopicInfoEntity>?,
    private val mCallback: RecyclerViewListener<TopicInfoEntity, View>
) : RecyclerView.Adapter<TopicListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_topic, parent, false)
        val holder = TopicListViewHolder(view)
        var position: Int
        var item: TopicInfoEntity?

        holder.itemView.setOnClickListener {
            position = holder.adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                item = list?.get(position)
                item?.run {
                    mCallback.onClickItemForViewId(this, it, view)
                }
            }
        }

        return holder
    }

    override fun getItemCount(): Int {
        return list?.count() ?: 0
    }

    override fun onBindViewHolder(holder: TopicListViewHolder, position: Int) {
        list?.get(position)?.let {
            holder.containerView.topic_number.text = (position + 1).toString()
            holder.containerView.topic_title.text = it.title
            holder.containerView.num_agree.text = it.sides[0].vote_count.toString()
            holder.containerView.num_disagree.text = it.sides[1].vote_count.toString()
            holder.containerView.end_date.text = it.end_date
            holder.containerView.num_reply.text = it.reply_count.toString()
            glide?.load(it.img_url)?.into(holder.containerView.img_topic)
        }
    }
}