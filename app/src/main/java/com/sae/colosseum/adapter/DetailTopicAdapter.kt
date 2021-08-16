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
import com.sae.colosseum.interfaces.RecyclerViewListener
import com.sae.colosseum.model.entity.TopicInfoEntity
import com.sae.colosseum.utils.GlobalApplication
import kotlinx.android.synthetic.main.header_topic.view.*
import kotlinx.android.synthetic.main.item_reply.view.*

class DetailTopicAdapter(
    var topicInfo: TopicInfoEntity?,
    private val headerListener: RecyclerViewListener<View, Int, View>,
    private val itemListener: RecyclerViewListener<View, Int, View>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_HEADER = 0
    private val TYPE_ITEM = 1

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

    class HeaderTopicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    class ReplyTopicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
        val holder: RecyclerView.ViewHolder
        var position: Int

        when(viewType) {
            TYPE_HEADER -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.header_topic, parent, false)
                holder = HeaderTopicViewHolder(view)
                View.OnClickListener {
                    position = holder.adapterPosition
                    headerListener.onClickItem(it, position, view)
                }.let {
                    // 좋아요/싫어요 클릭 리스너
                    holder.itemView.run {
                        wrap_edit_reply.setOnClickListener(it)
                        btn_ok.setOnClickListener(it)
                        btn_vote.setOnClickListener(it)
                        txt_up.setOnClickListener(it)
                        txt_down.setOnClickListener(it)
                        wrap_cont.setOnClickListener(it)
                    }
                }

                holder.itemView.character_limit.text = holder.itemView.context.getString(R.string.character_limit, "0")
            }
            else -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.item_reply, parent, false)
                holder = ReplyTopicViewHolder(view)
                View.OnClickListener {
                    position = holder.adapterPosition - 1
                    itemListener.onClickItem(it, position, view)
                }.let {
                    holder.itemView.run {
                        img_like.setOnClickListener(it)
                        img_dislike.setOnClickListener(it)
                        btn_menu.setOnClickListener(it)
                        num_re_reply.setOnClickListener(it)
                    }
                }
            }
        }
        return holder
    }

    override fun getItemCount(): Int {
        val replyCount = topicInfo?.replies?.count() ?: 0
        return replyCount + 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        topicInfo?.let {
            holder.itemView.run {
                if (holder is ReplyTopicViewHolder) {
                    val replies = topicInfo?.replies?.get(position - 1)

                    list.adapter = null

                    val boldSpan: StyleSpan = StyleSpan(Typeface.BOLD)
                    val spannableStringBuilder = SpannableStringBuilder()
                    var mySide = ""
                    var mySideColor = R.color.colorPrimary
                    val nickName = replies?.user?.nick_name + " "
                    val reply = replies?.content

                    if (replies?.side_id == topicInfo?.sides?.get(0)?.id) {
                        mySide = "찬 "
                        mySideColor = R.color.colorUp
                    } else if (replies?.side_id == topicInfo?.sides?.get(1)?.id) {
                        mySide = "반 "
                        mySideColor = R.color.colorDown
                    }
                    spannableStringBuilder.append(mySide)
                    spannableStringBuilder.append(nickName)
                    spannableStringBuilder.append(reply)

                    val spannable = SpannableString(spannableStringBuilder)
                    val indexStart = mySide.length
                    val indexEnd = indexStart + nickName.length

                    spannable.setSpan(
                        ForegroundColorSpan(
                            ContextCompat.getColor(
                                context,
                                mySideColor
                            )
                        ), 0, indexStart, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    spannable.setSpan(
                        boldSpan,
                        indexStart,
                        indexEnd,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )

                    content.text = spannable
                    txt_date.text = replies?.updated_at
                    num_like.text = replies?.like_count.toString()
                    num_dislike.text = replies?.dislike_count.toString()
                    val replyCount = replies?.reply_count?.toString() ?: ""

                    num_re_reply.text = context.getString(R.string.ko_re_reply, replyCount)

                    //                    내 댓글만 수정 메뉴 보임
                    val replyId = replies?.user?.id
                    val myId = GlobalApplication.prefs.userId

                    if (replyId == myId) btn_menu.visibility = VISIBLE
                    else btn_menu.visibility = GONE

                    //                    좋아요/싫어요 상태
                    when {
                        replies?.my_like ?: run { false } -> {
                            setRes(img_like, R.drawable.like_on)
                            setRes(img_dislike, R.drawable.like_off)
                        }
                        replies?.my_dislike ?: run { false } -> {
                            setRes(img_like, R.drawable.like_off)
                            setRes(img_dislike, R.drawable.dislike_on)
                        }
                        else -> {
                            setRes(img_like, R.drawable.like_off)
                            setRes(img_dislike, R.drawable.dislike_off)
                        }
                    }
                } else if (holder is HeaderTopicViewHolder) {

                    val up = it.sides?.get(0)
                    val down = it.sides?.get(1)
                    val upCount = up?.vote_count ?: 0
                    val downCount = down?.vote_count ?: 0

                    txt_nick_name.text = GlobalApplication.prefs.userNickName
                    txt_topic.text = it.title
                    txt_start_data.text = it.start_date
                    num_up.text = upCount.toString()
                    num_down.text = downCount.toString()

                    val replyCount = it.reply_count?.toString() ?: ""

                    num_reply.text = context.getString(R.string.ko_num_reply, replyCount)
                    Glide.with(context).load(it.img_url).into(img_topic)

                    val voteSum = (upCount + downCount).toFloat()
                    val percentUp = upCount / voteSum
                    val percentDown = downCount / voteSum

                    (bar_up.layoutParams as ConstraintLayout.LayoutParams)
                        .matchConstraintPercentWidth = percentUp
                    bar_up.requestLayout()

                    (bar_down.layoutParams as ConstraintLayout.LayoutParams)
                        .matchConstraintPercentWidth = percentDown
                    bar_down.requestLayout()

                    //                    유저 찬/반 정보에 따라 버튼 색 초기화
                    when (it.my_side_id) {
                        up?.id -> {
                            setRes(check_up, R.drawable.ico_check_up)
                            setRes(check_down, R.drawable.ico_check)
                        }
                        down?.id -> {
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
                            val character = s?.length ?: 0
                            holder.itemView.btn_ok.isEnabled = character > 5
                            holder.itemView.character_limit.text =
                                holder.itemView.character_limit.context.getString(
                                    R.string.character_limit,
                                    character.toString()
                                )
                        }

                        override fun beforeTextChanged(
                            s: CharSequence?,
                            start: Int,
                            count: Int,
                            after: Int
                        ) {
                        }

                        override fun onTextChanged(
                            s: CharSequence?,
                            start: Int,
                            before: Int,
                            count: Int
                        ) {
                        }
                    })
                }
            }
        }
    }

    private fun setRes(v: ImageView, resId: Int) {
        v.setImageResource(resId)
    }


}