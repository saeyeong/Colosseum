package com.sae.colosseum.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sae.colosseum.R
import com.sae.colosseum.interfaces.RecyclerViewListener
import com.sae.colosseum.model.entity.TopicInfoEntity
import com.sae.colosseum.utils.GlobalApplication
import kotlinx.android.synthetic.main.item_topic.view.*

open class TopicListAdapter(
    var list: ArrayList<TopicInfoEntity>?,
    private val mCallback: RecyclerViewListener<View, Int, View>
) : RecyclerView.Adapter<TopicListAdapter.ListTopicViewHolder>() {

    class ListTopicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicListAdapter.ListTopicViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_topic, parent, false)
        val holder = ListTopicViewHolder(view)
        var position: Int

        holder.itemView.setOnClickListener {
            position = holder.adapterPosition
            if (position != RecyclerView.NO_POSITION) mCallback.onClickItem(it, position, view)
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

                val upCount = it.sides?.get(0)?.vote_count?.toString() ?: ""
                val downCount = it.sides?.get(1)?.vote_count?.toString() ?: ""
                val replyCount = it.reply_count?.toString() ?: ""

                num_up.text = context.getString(R.string.ko_num_up, upCount)
                num_down.text = context.getString(R.string.ko_num_down, downCount)
                num_reply.text = context.getString(R.string.ko_num_reply, replyCount)

                end_date.text = it.end_date
                Glide.with(GlobalApplication.instance.applicationContext).load(it.img_url).into(img_topic)
            }
        }
    }
}