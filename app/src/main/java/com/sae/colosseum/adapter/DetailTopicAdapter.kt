package com.sae.colosseum.adapter

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sae.colosseum.R
import com.sae.colosseum.adapter.holder.HeaderTopicViewHolder
import com.sae.colosseum.adapter.holder.ReplyTopicViewHolder
import com.sae.colosseum.interfaces.RecyclerViewListener
import com.sae.colosseum.model.entity.DataEntity
import com.sae.colosseum.model.entity.RepliesEntity
import com.sae.colosseum.utils.GlobalApplication
import kotlinx.android.synthetic.main.header_topic.view.*
import kotlinx.android.synthetic.main.item_reply.view.*

class DetailTopicAdapter(
    data: DataEntity?,
    private val itemListener: RecyclerViewListener<RepliesEntity, View>,
    private val headerListener: RecyclerViewListener<RepliesEntity, View>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_HEADER = 0
    private val TYPE_ITEM = 1
    private val topic = data?.topic
    private val replies = data?.topic?.replies
    private var upId: Int = topic?.sides?.get(0)?.id ?: -1
    private var downId: Int = topic?.sides?.get(1)?.id ?: -1
    private var mySideId = topic?.my_side_id

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
        var item: RepliesEntity?

        when(viewType) {
            TYPE_HEADER -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.header_topic, parent, false)
                holder = HeaderTopicViewHolder(view)
                View.OnClickListener {
                    position = holder.adapterPosition
                    item = replies?.get(position)
                    item?.run {
                        itemListener.onClickItem(this, it, view)
                    }
                }.let {
                    // 좋아요/싫어요 클릭 리스너
                    holder.itemView.run {
                        edit_wrap.setOnClickListener(it)
                        btn_ok.setOnClickListener(it)
                        up_wrap.setOnClickListener(it)
                        down_wrap.setOnClickListener(it)
                        wrap_topic.setOnClickListener(it)
                    }
                }
//                의견 입력창에 입력한 글자 개수 보여주기
                holder.itemView.edit_reply.addTextChangedListener(object : TextWatcher {
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
            else -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.item_reply, parent, false)
                holder = ReplyTopicViewHolder(view)
                View.OnClickListener {
                    position = holder.adapterPosition
                    item = replies?.get(position)
                    item?.run {
                        headerListener.onClickItem(this, it, view)
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
        return replies?.count() ?: 0 + 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ReplyTopicViewHolder) {
            replies?.get(position)?.let {
                holder.itemView.run {
                    nick_name.text = it.user?.nick_name
                    content.text = it.content
                    txt_date.text = it.updated_at
                    num_like.text = it.like_count.toString()
                    num_dislike.text = it.dislike_count.toString()
                    num_re_reply.text = it.reply_count.toString()

                    if (it.side_id == upId) {
                        userInfo.setBackgroundResource(R.color.colorUp)
                        userInfo.background.alpha = 50
                    } else if (it.side_id == downId) {
                        userInfo.setBackgroundResource(R.color.colorDown)
                        userInfo.background.alpha = 50
                    }

//                    내 댓글만 수정 메뉴 보임
                    if (it.user?.id == GlobalApplication.loginUser.id) {
                        btn_menu.visibility = VISIBLE
                    }
//                    좋아요/싫어요 상태
                    if (it.my_like ?: run { false }) {
                        img_like.setImageResource(R.drawable.like_on)
                        img_dislike.setImageResource(R.drawable.like_off)
                    } else if (it.my_dislike ?: run { false }) {
                        img_like.setImageResource(R.drawable.like_off)
                        img_dislike.setImageResource(R.drawable.dislike_on)
                    } else {
                        img_like.setImageResource(R.drawable.like_off)
                        img_dislike.setImageResource(R.drawable.dislike_off)
                    }
                }
            }
        } else if(holder is HeaderTopicViewHolder) {
            holder.itemView.txt_nick_name.text = GlobalApplication.loginUser.nick_name

            topic?.let {
                holder.itemView.run {
                    txt_title.text = it.title
                    txt_start_data.text = it.start_date
                    num_up.text = it.sides[0].vote_count.toString()
                    num_down.text = it.sides[1].vote_count.toString()
                    num_reply.text = it.reply_count.toString()
                    Glide.with(img_topic.context).load(it.img_url).into(img_topic)

                    fun clickUpColor() {
                        txt_up.setTextColor(ContextCompat.getColor(txt_up.context, R.color.colorUp))
                        num_up.setTextColor(ContextCompat.getColor(num_up.context, R.color.colorUp))
                        txt_down.setTextColor(ContextCompat.getColor(txt_down.context, android.R.color.tab_indicator_text))
                        num_down.setTextColor(ContextCompat.getColor(num_down.context, android.R.color.tab_indicator_text))
                    }

                    fun clickDownColor() {
                        txt_up.setTextColor(ContextCompat.getColor(txt_up.context, android.R.color.tab_indicator_text))
                        num_up.setTextColor(ContextCompat.getColor(num_up.context, android.R.color.tab_indicator_text))
                        txt_down.setTextColor(ContextCompat.getColor(txt_down.context, R.color.colorDown))
                        num_down.setTextColor(ContextCompat.getColor(num_down.context, R.color.colorDown))
                    }

                    fun noneColor() {
                        txt_up.setTextColor(ContextCompat.getColor(txt_up.context, android.R.color.tab_indicator_text))
                        num_up.setTextColor(ContextCompat.getColor(num_up.context, android.R.color.tab_indicator_text))
                        txt_down.setTextColor(ContextCompat.getColor(txt_down.context, android.R.color.tab_indicator_text))
                        num_down.setTextColor(ContextCompat.getColor(num_down.context, android.R.color.tab_indicator_text))
                    }

//                    유저 찬/반 정보에 따라 버튼 색 초기화
                    if (mySideId == upId) {
                        clickUpColor()
                    } else if (mySideId == downId) {
                        clickDownColor()
                    } else {
                        noneColor()
                    }

                }
            }
        }
    }


}