package com.sae.colosseum.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sae.colosseum.R
import com.sae.colosseum.databinding.ItemTopicBinding
import com.sae.colosseum.interfaces.RecyclerViewListener
import com.sae.colosseum.model.entity.TopicInfoEntity
import com.sae.colosseum.utils.GlobalApplication

open class TopicListAdapter(
    var list: ArrayList<TopicInfoEntity>?,
    private val mCallback: RecyclerViewListener<View, Int, View>
) : RecyclerView.Adapter<TopicListAdapter.ViewHolder>() {

    class ViewHolder(
        val viewBinding: ItemTopicBinding
    ) : RecyclerView.ViewHolder(
        viewBinding.root
    ) {

        fun bindViewHolder(item: TopicInfoEntity, position: Int) {

            viewBinding.topicNumber.text = (position + 1).toString()
            viewBinding.topicTitle.text = item.title

            val upCount = item.sides?.get(0)?.vote_count?.toString() ?: ""
            val downCount = item.sides?.get(1)?.vote_count?.toString() ?: ""
            val replyCount = item.reply_count?.toString() ?: ""

            viewBinding.numUp.text = viewBinding.numUp.context.getString(R.string.ko_num_up, upCount)
            viewBinding.numDown.text = viewBinding.numDown.context.getString(R.string.ko_num_down, downCount)
            viewBinding.numReply.text = viewBinding.numReply.context.getString(R.string.ko_num_reply, replyCount)

            viewBinding.endDate.text = item.end_date
            Glide.with(GlobalApplication.instance.applicationContext).load(item.img_url).into(viewBinding.imgTopic)

            viewBinding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemTopicBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = ViewHolder(view)

        holder.viewBinding.root.setOnClickListener {
            if (holder.bindingAdapterPosition != RecyclerView.NO_POSITION)
                mCallback.onClickItem(it, holder.bindingAdapterPosition, view.root)
        }

        return holder
    }

    override fun getItemCount(): Int {
        return list?.count() ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        list?.get(position)?.let {
            holder.bindViewHolder(it, position)
        }
    }
}