package com.sae.colosseum.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sae.colosseum.R
import com.sae.colosseum.adapter.holder.NotificationListViewHolder
import com.sae.colosseum.model.entity.NotificationEntity
import kotlinx.android.synthetic.main.item_notification.view.*

class NotificationListAdapter(private val list: ArrayList<NotificationEntity>?) : RecyclerView.Adapter<NotificationListViewHolder>() {
    lateinit var type: String
    private var setIconId: Int = 0
    private var txtAlarm: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_notification, parent, false)
        return NotificationListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list?.count() ?: 0
    }

    override fun onBindViewHolder(holder: NotificationListViewHolder, position: Int) {
        list?.get(position)?.let {

            holder.itemView.run {
                type = it.type
                when(type) {
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

                if (setIconId != 0) type_icon.setImageResource(setIconId)
                nick_name.text = it.act_user.nick_name
                txt_alarm.text = txt_alarm.context.getString(txtAlarm)
            }
        }
    }
}