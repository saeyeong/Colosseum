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
import com.sae.colosseum.databinding.ItemReReplyBinding
import com.sae.colosseum.interfaces.RecyclerViewListener
import com.sae.colosseum.model.entity.ReplyEntity
import com.sae.colosseum.utils.GlobalApplication
import java.util.*

class ReReplyAdapter(
    var list: ArrayList<ReplyEntity>?,
    private val mCallback: RecyclerViewListener<View, Int, View>
) : RecyclerView.Adapter<ReReplyAdapter.ViewHolder>() {

    class ViewHolder(
        val viewBinding: ItemReReplyBinding
    ) : RecyclerView.ViewHolder(
        viewBinding.root
    ) {

        fun bindViewHolder(item: ReplyEntity) {
            val boldSpan = StyleSpan(Typeface.BOLD)
            val spannableStringBuilder = SpannableStringBuilder()
            val nickName = item.user?.nick_name+" "
            val reply = item.content

            spannableStringBuilder.append(nickName)
            spannableStringBuilder.append(reply)

            val spannable = SpannableString(spannableStringBuilder)
            val indexStart = 0
            val indexEnd = nickName.length

            spannable.setSpan(boldSpan, indexStart, indexEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

            viewBinding.content.text = spannable
            viewBinding.txtDate.text = item.updated_at

            //            내 댓글만 수정 메뉴 보임
            val replyId = item.user?.id
            val myId = GlobalApplication.prefs.userId

            if (replyId == myId)
                viewBinding.btnMenu.visibility = VISIBLE
            else
                viewBinding.btnMenu.visibility = View.GONE

            viewBinding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemReReplyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = ViewHolder(view)

        holder.viewBinding.btnMenu.setOnClickListener {
            mCallback.onClickItem(it, holder.bindingAdapterPosition, view.root)
        }
        return holder
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