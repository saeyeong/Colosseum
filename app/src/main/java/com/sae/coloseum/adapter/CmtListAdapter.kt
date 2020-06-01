package com.sae.coloseum.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sae.coloseum.R
import com.sae.coloseum.adapter.holder.CmtListViewHolder
import com.sae.coloseum.model.entity.CmtListEntity
import kotlinx.android.synthetic.main.item_cmt.view.*

class CmtListAdapter(private val list: List<CmtListEntity>?) : RecyclerView.Adapter<CmtListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CmtListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cmt, parent, false)
        return CmtListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list?.count()?:0
    }

    override fun onBindViewHolder(holder: CmtListViewHolder, position: Int) {
        list?.get(position)?.let {
            holder.containerView.cmt_nickname.text = it.cmtNickname
            holder.containerView.txt_date_cmt.text = it.txtDateCmt
            holder.containerView.num_like.text = it.numLike
            holder.containerView.num_dislike.text = it.numDislike
            holder.containerView.cmt_detail.text = it.txtCmt
        }
    }
}