package com.sae.coloseum.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sae.coloseum.R
import com.sae.coloseum.adapter.holder.AlarmListViewHolder
import com.sae.coloseum.model.entity.AlarmListEntity
import kotlinx.android.synthetic.main.item_alarm.view.*

class AlarmListAdapter(private val list: ArrayList<AlarmListEntity>?) : RecyclerView.Adapter<AlarmListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_alarm, parent, false)
        return AlarmListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list?.count()?:0
    }

    override fun onBindViewHolder(holder: AlarmListViewHolder, position: Int) {
        list?.get(position)?.let {
            holder.containerView.type_icon.setImageResource(it.iconType)
            holder.containerView.nickname.text = it.nickname
            holder.containerView.txt_alarm.setText(R.string.heart_like_alarm)
        }
    }
}