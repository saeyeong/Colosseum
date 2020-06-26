package com.sae.colosseum.adapter

import android.graphics.Typeface
import android.text.*
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sae.colosseum.R
import com.sae.colosseum.adapter.holder.HeaderTopicViewHolder
import com.sae.colosseum.adapter.holder.ReplyTopicViewHolder
import com.sae.colosseum.interfaces.RecyclerViewListener
import com.sae.colosseum.model.entity.ReplyEntity
import com.sae.colosseum.model.entity.TopicInfoEntity
import com.sae.colosseum.utils.GlobalApplication
import kotlinx.android.synthetic.main.header_topic.view.*
import kotlinx.android.synthetic.main.item_reply.view.*

class DetailTopicAdapter(
    var topicInfo: TopicInfoEntity?,
    private val headerListener: RecyclerViewListener<ReplyEntity, View>,
    private val itemListener: RecyclerViewListener<ReplyEntity, View>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_HEADER = 0
    private val TYPE_ITEM = 1
    private val boldSpan: StyleSpan = StyleSpan(Typeface.BOLD)

    override fun getItemViewType(position: Int): Int {
        return when(position) {
            0 -> {
                TYPE_HEADER
            }
            else -> {
                TYPE_ITEM
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
        val holder: RecyclerView.ViewHolder
        var position: Int
        var item: ReplyEntity?

        when(viewType) {
            TYPE_HEADER -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.header_topic, parent, false)
                holder = HeaderTopicViewHolder(view)
                View.OnClickListener {
                    position = holder.adapterPosition
                    item = topicInfo?.replies?.get(position)
                    item?.run {
                        headerListener.onClickItem(this, it, view)
                    }
                }.let {
                    // 좋아요/싫어요 클릭 리스너
                    holder.itemView.run {
                        edit_wrap.setOnClickListener(it)
                        btn_ok.setOnClickListener(it)
                        btn_vote.setOnClickListener(it)
                        wrap_up.setOnClickListener(it)
                        wrap_down.setOnClickListener(it)
                        wrap_topic.setOnClickListener(it)
                    }
                }
            }
            else -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.item_reply, parent, false)
                holder = ReplyTopicViewHolder(view)
                View.OnClickListener {
                    position = holder.adapterPosition
                    item = topicInfo?.replies?.get(position-1)
                    item?.run {
                        itemListener.onClickItem(this, it, view)
                    }
                }.let {
                    // 좋아요/싫어요 클릭 리스너
                    holder.itemView.run {
                        like_wrap.setOnClickListener(it)
                        dislike_wrap.setOnClickListener(it)
                        btn_menu.setOnClickListener(it)
                        btn_re_reply.setOnClickListener(it)
                    }
                }
            }
        }
        return holder
    }

    override fun getItemCount(): Int {
        return topicInfo?.replies?.count() ?: 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ReplyTopicViewHolder) {

            topicInfo?.replies?.get(position-1)?.let {
                holder.itemView.run {

                    val spannableStringBuilder = SpannableStringBuilder()
                    var mySide = ""
                    var mySideColor = R.color.colorPrimary
                    val nickName = it.user?.nick_name+" "
                    val reply = it.content

                    if (it.side_id == topicInfo?.sides?.get(0)?.id) {
                        mySide = "찬 "
                        mySideColor = R.color.colorUp
                    } else if (it.side_id == topicInfo?.sides?.get(1)?.id) {
                        mySide = "반 "
                        mySideColor = R.color.colorDown
                    }
                    spannableStringBuilder.append(mySide)
                    spannableStringBuilder.append(nickName)
                    spannableStringBuilder.append(reply)

                    val spannable = SpannableString(spannableStringBuilder)
                    val indexStart = mySide.length
                    val indexEnd = indexStart + nickName.length

                    spannable.setSpan(ForegroundColorSpan(ContextCompat.getColor(context, mySideColor)), 0, indexStart, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    spannable.setSpan(boldSpan, indexStart, indexEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

                    content.text = spannable
                    txt_date.text = it.updated_at
                    num_like.text = it.like_count.toString()
                    num_dislike.text = it.dislike_count.toString()
                    num_re_reply.text = it.reply_count.toString()

//                    내 댓글만 수정 메뉴 보임
                    if (it.user?.id == GlobalApplication.prefs.userId) {
                        btn_menu.visibility = VISIBLE
                    }

//                    좋아요/싫어요 상태
                    when {
                        it.my_like ?: run { false } -> {
                            setRes(img_like, R.drawable.like_on)
                            setRes(img_dislike, R.drawable.like_off)
                        }
                        it.my_dislike ?: run { false } -> {
                            setRes(img_like, R.drawable.like_off)
                            setRes(img_dislike, R.drawable.dislike_on)
                        }
                        else -> {
                            setRes(img_like, R.drawable.like_off)
                            setRes(img_dislike, R.drawable.dislike_off)
                        }
                    }
                }
            }
        } else if(holder is HeaderTopicViewHolder) {

            topicInfo?.let {
                holder.itemView.run {
                    txt_nick_name.text = GlobalApplication.prefs.userNickName
                    txt_title.text = it.title
                    txt_start_data.text = it.start_date
                    num_up.text = it.sides[0].vote_count.toString()
                    num_down.text = it.sides[1].vote_count.toString()
                    num_reply.text = it.reply_count.toString()
                    Glide.with(img_topic.context).load(it.img_url).into(img_topic)

                    val voteSum = (it.sides[0].vote_count + it.sides[1].vote_count).toFloat()
                    val percentUp = it.sides[0].vote_count / voteSum
                    val percentDown = it.sides[1].vote_count / voteSum

                    (bar_up.layoutParams as ConstraintLayout.LayoutParams)
                        .matchConstraintPercentWidth = percentUp
                    bar_up.requestLayout()

                    (bar_down.layoutParams as ConstraintLayout.LayoutParams)
                        .matchConstraintPercentWidth = percentDown
                    bar_down.requestLayout()

//                    유저 찬/반 정보에 따라 버튼 색 초기화
                    when (it.my_side_id) {
                        it.sides[0].id -> {
                            setRes(check_up, R.drawable.ico_check_up)
                            setRes(check_down, R.drawable.ico_check)
                        }
                        it.sides[1].id -> {
                            setRes(check_up, R.drawable.ico_check)
                            setRes(check_down, R.drawable.ico_check_down)
                        }
                        else -> {
                            setRes(check_up, R.drawable.ico_check)
                            setRes(check_down, R.drawable.ico_check)
                        }
                    }

//                의견 입력창에 입력한 글자 개수 보여주기
                    edit_reply.addTextChangedListener(object : TextWatcher {
                        override fun afterTextChanged(s: Editable?) {
                            holder.itemView.btn_ok.isEnabled = (s?.length ?: 0) > 5
                            holder.itemView.num_characters.text = s?.length.toString()
                        }
                        override fun beforeTextChanged(
                            s: CharSequence?,
                            start: Int,
                            count: Int,
                            after: Int
                        ) {}
                        override fun onTextChanged(
                            s: CharSequence?,
                            start: Int,
                            before: Int,
                            count: Int
                        ) {}
                    })

                }
            }
        }
    }

    private fun setRes(v: ImageView, resId: Int) {
        v.setImageResource(resId)
    }


}