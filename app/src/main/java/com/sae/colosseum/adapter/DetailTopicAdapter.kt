package com.sae.colosseum.adapter

import android.graphics.Typeface
import android.text.*
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
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
import com.sae.colosseum.databinding.ItemReplyBinding
import com.sae.colosseum.interfaces.RecyclerViewListener
import com.sae.colosseum.model.entity.ReplyEntity
import com.sae.colosseum.model.entity.TopicInfoEntity
import com.sae.colosseum.utils.GlobalApplication

class DetailTopicAdapter(
    var topicInfo: TopicInfoEntity?,
    private val itemListener: RecyclerViewListener<View, Int, View>
) : RecyclerView.Adapter<DetailTopicAdapter.ViewHolder>() {

    class ViewHolder(
        val viewBinding: ItemReplyBinding
    ) : RecyclerView.ViewHolder(
        viewBinding.root
    ) {

        fun bindViewHolder(item: TopicInfoEntity, position: Int) {

            val reply = item.replies?.get(position)
            val boldSpan = StyleSpan(Typeface.BOLD)
            val spannableStringBuilder = SpannableStringBuilder()
            var mySide = ""
            var mySideColor = R.color.colorPrimary
            val nickName = reply?.user?.nick_name + " "

            if (reply?.side_id == item.sides?.get(0)?.id) {
                mySide = "찬 "
                mySideColor = R.color.colorUp
            } else if (reply?.side_id == item.sides?.get(1)?.id) {
                mySide = "반 "
                mySideColor = R.color.colorDown
            }
            spannableStringBuilder.append(mySide)
            spannableStringBuilder.append(nickName)
            spannableStringBuilder.append(reply?.content)

            val spannable = SpannableString(spannableStringBuilder)
            val indexStart = mySide.length
            val indexEnd = indexStart + nickName.length

            spannable.setSpan(
                ForegroundColorSpan(
                    ContextCompat.getColor(
                        viewBinding.content.context,
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

            viewBinding.content.text = spannable
            viewBinding.txtDate.text = reply?.updated_at
            viewBinding.numLike.text = reply?.like_count.toString()
            viewBinding.numDislike.text = reply?.dislike_count.toString()
            val replyCount = reply?.reply_count?.toString() ?: ""

            viewBinding.numReReply.text = viewBinding.numReReply.context.getString(R.string.ko_re_reply, replyCount)

            //                    내 댓글만 수정 메뉴 보임
            val replyId = reply?.user?.id
            val myId = GlobalApplication.prefs.userId

            if (replyId == myId) viewBinding.btnMenu.visibility = VISIBLE
            else viewBinding.btnMenu.visibility = GONE

            //                    좋아요/싫어요 상태
//            when {
//                reply?.my_like ?: run { false } -> {
//                    setRes(viewBinding.imgLike, R.drawable.like_on)
//                    setRes(viewBinding.imgDislike, R.drawable.like_off)
//                }
//                replies?.my_dislike ?: run { false } -> {
//                    setRes(viewBinding.imgLike, R.drawable.like_off)
//                    setRes(viewBinding.imgDislike, R.drawable.dislike_on)
//                }
//                else -> {
//                    setRes(viewBinding.imgLike, R.drawable.like_off)
//                    setRes(viewBinding.imgDislike, R.drawable.dislike_off)
//                }
//            }

            viewBinding.executePendingBindings()
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemReplyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = ViewHolder(view)
        val position = holder.bindingAdapterPosition

        View.OnClickListener {
            itemListener.onClickItem(it, position, view.root)
        }.let {
            holder.viewBinding.run {
                imgLike.setOnClickListener(it)
                imgDislike.setOnClickListener(it)
                btnMenu.setOnClickListener(it)
                numReReply.setOnClickListener(it)
            }
        }

        return holder
    }

    override fun getItemCount(): Int {
        return topicInfo?.replies?.count() ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        topicInfo?.let {
            holder.bindViewHolder(it, position)

//                if (holder is ReplyTopicViewHolder) {
//
//                } else if (holder is HeaderTopicViewHolder) {
//
//                    val up = it.sides?.get(0)
//                    val down = it.sides?.get(1)
//                    val upCount = up?.vote_count ?: 0
//                    val downCount = down?.vote_count ?: 0
//
//                    txt_nick_name.text = GlobalApplication.prefs.userNickName
//                    txt_topic.text = it.title
//                    txt_start_data.text = it.start_date
//                    num_up.text = upCount.toString()
//                    num_down.text = downCount.toString()
//
//                    val replyCount = it.reply_count?.toString() ?: ""
//
//                    num_reply.text = context.getString(R.string.ko_num_reply, replyCount)
//                    Glide.with(context).load(it.img_url).into(img_topic)
//
//                    val voteSum = (upCount + downCount).toFloat()
//                    val percentUp = upCount / voteSum
//                    val percentDown = downCount / voteSum
//
//                    (bar_up.layoutParams as ConstraintLayout.LayoutParams)
//                        .matchConstraintPercentWidth = percentUp
//                    bar_up.requestLayout()
//
//                    (bar_down.layoutParams as ConstraintLayout.LayoutParams)
//                        .matchConstraintPercentWidth = percentDown
//                    bar_down.requestLayout()
//
//                    //                    유저 찬/반 정보에 따라 버튼 색 초기화
//                    when (it.my_side_id) {
//                        up?.id -> {
//                            setRes(check_up, R.drawable.ico_check_up)
//                            setRes(check_down, R.drawable.ico_check)
//                        }
//                        down?.id -> {
//                            setRes(check_up, R.drawable.ico_check)
//                            setRes(check_down, R.drawable.ico_check_down)
//                        }
//                        else -> {
//                            setRes(check_up, R.drawable.ico_check)
//                            setRes(check_down, R.drawable.ico_check)
//                        }
//                    }
//
//
//                    //                의견 입력창에 입력한 글자 개수 보여주기
//                    edit_reply.addTextChangedListener(object : TextWatcher {
//                        override fun afterTextChanged(s: Editable?) {
//                            val character = s?.length ?: 0
//                            holder.itemView.btn_ok.isEnabled = character > 5
//                            holder.itemView.character_limit.text =
//                                holder.itemView.character_limit.context.getString(
//                                    R.string.character_limit,
//                                    character.toString()
//                                )
//                        }
//
//                        override fun beforeTextChanged(
//                            s: CharSequence?,
//                            start: Int,
//                            count: Int,
//                            after: Int
//                        ) {
//                        }
//
//                        override fun onTextChanged(
//                            s: CharSequence?,
//                            start: Int,
//                            before: Int,
//                            count: Int
//                        ) {
//                        }
//                    })
//                }
        }
    }

//    private fun setRes(v: ImageView, resId: Int) {
//        v.setImageResource(resId)
//    }

}