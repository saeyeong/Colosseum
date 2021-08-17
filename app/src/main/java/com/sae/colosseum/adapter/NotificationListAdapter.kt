package com.sae.colosseum.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sae.colosseum.R
import com.sae.colosseum.databinding.ItemNotificationBinding
import com.sae.colosseum.databinding.ItemReplyBinding
import com.sae.colosseum.model.entity.NotificationEntity
import com.sae.colosseum.model.entity.TopicInfoEntity

class NotificationListAdapter(private val list: ArrayList<NotificationEntity>?) : RecyclerView.Adapter<NotificationListAdapter.ViewHolder>() {

    class ViewHolder(
        val viewBinding: ItemNotificationBinding
    ) : RecyclerView.ViewHolder(
        viewBinding.root
    ) {

        fun bindViewHolder(item: NotificationEntity) {
            var setIconId = 0
            var txtAlarm = 0

            when(item.type) {
                "답글달림" -> {
                    setIconId = R.drawable.comment
                    txtAlarm = R.string.alarm_reply

                }
                "의견좋아요" -> {
                    setIconId = R.drawable.like_on
                    txtAlarm = R.string.alarm_like
                }
                "의견싫어요" -> {
                    setIconId = R.drawable.like_off
                    txtAlarm = R.string.alarm_dislike
                }
            }

            if (setIconId != 0)
                viewBinding.typeIcon.setImageResource(setIconId)

            viewBinding.nickName.text = item.act_user.nick_name
            viewBinding.txtAlarm.text = viewBinding.txtAlarm.context.getString(txtAlarm)

            viewBinding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
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