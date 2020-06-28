package com.sae.colosseum.adapter

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sae.colosseum.R
import com.sae.colosseum.adapter.holder.ReReplyViewHolder
import com.sae.colosseum.interfaces.RecyclerViewListener
import com.sae.colosseum.model.entity.ReplyEntity
import com.sae.colosseum.utils.GlobalApplication
import kotlinx.android.synthetic.main.item_reply.view.*
import java.util.*

class ReReplyAdapter(
    var list: ArrayList<ReplyEntity>?,
    private val mCallback: RecyclerViewListener<View, Int, View>
) : RecyclerView.Adapter<ReReplyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReReplyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_re_reply, parent, false)
        val holder = ReReplyViewHolder(view)
        var position: Int

        holder.itemView.btn_menu.setOnClickListener {
            position = holder.adapterPosition
            mCallback.onClickItem(it, position, view)
        }

        return holder
    }

    override fun getItemCount(): Int {
        return list?.count() ?: 0
    }

    override fun onBindViewHolder(holder: ReReplyViewHolder, position: Int) {
        list?.get(position)?.let {

            val boldSpan: StyleSpan = StyleSpan(Typeface.BOLD)
            val spannableStringBuilder = SpannableStringBuilder()
            val nickName = it.user?.nick_name+" "
            val reply = it.content

            spannableStringBuilder.append(nickName)
            spannableStringBuilder.append(reply)

            val spannable = SpannableString(spannableStringBuilder)
            val indexStart = 0
            val indexEnd = nickName.length

            spannable.setSpan(boldSpan, indexStart, indexEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

            holder.itemView.content.text = spannable
            holder.itemView.txt_date.text = it.updated_at

            //            내 댓글만 수정 메뉴 보임
            val replyId = it.user?.id
            val myId = GlobalApplication.prefs.userId

            if (replyId == myId) holder.itemView.btn_menu.visibility = VISIBLE
            else holder.itemView.btn_menu.visibility = View.GONE
        }
    }
}