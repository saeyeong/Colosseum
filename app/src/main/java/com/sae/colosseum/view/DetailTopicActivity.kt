package com.sae.colosseum.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.*
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sae.colosseum.R
import com.sae.colosseum.adapter.DetailTopicAdapter
import com.sae.colosseum.adapter.ReReplyAdapter
import com.sae.colosseum.databinding.ActivityTopicDetailBinding
import com.sae.colosseum.interfaces.RecyclerViewListener
import com.sae.colosseum.utils.BaseActivity
import com.sae.colosseum.interfaces.ResultInterface
import com.sae.colosseum.model.entity.*
import kotlinx.android.synthetic.main.header_topic.*
import kotlinx.android.synthetic.main.header_topic.view.*
import kotlinx.android.synthetic.main.item_reply.*
import kotlinx.android.synthetic.main.item_reply.view.*

class DetailTopicActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityTopicDetailBinding
    lateinit var detailTopicAdapter: DetailTopicAdapter
    lateinit var reReplyAdapter: ReReplyAdapter
    var topicInfo: TopicInfoEntity? = null
    var topicUp: SidesEntity? = null
    var topicDown: SidesEntity? = null
    var userSideId: Int? = null
    var replyCheck: Int = 1 // 0 : 쓸수있음 1 : 이미 썼음
    var orderType: String = "NEW"
    var pageNum: Int = 1
    var replies: ArrayList<ReplyEntity>? = null
    var topicId: Int = -1
    lateinit var builder: AlertDialog.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_topic_detail)
        init()
    }

    fun init() {
        setListener()

        builder = AlertDialog.Builder(this)
        topicId = intent.getIntExtra("topicId", -1)
        replies = ArrayList()


        binding.list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    this@DetailTopicActivity.pageNum += 1
                    getTopic(orderType, pageNum)
                }
            }
        })

        val headerListener = object : RecyclerViewListener<View, Int, View> {
            override fun onClickItem(clickedView: View, position: Int, itemView: View) {
                when (clickedView.id) {
                    // 의견 영역 눌렀을 때
                    wrap_edit_reply.id -> {
                        reply_off.visibility = GONE
                        reply_on.visibility = VISIBLE
                    }
                    // 의견 등록하기 눌렀을 때
                    btn_ok.id -> {
                        when {
                            userSideId == -1 -> { // 투표 검사
                                toast("투표를 완료하시면 의견을 작성하실 수 있습니다")
                            }
                            replyCheck == 1 -> {
                                toast("의견을 이미 작성하셨습니다.")
                            }
                            else -> {
                                reply_on.visibility = GONE
                                reply_off.visibility = VISIBLE

                                val content = edit_reply.text.toString()
                                postTopicReply(content)

                                replyCheck = 1

                                btn_vote.isEnabled = false
                                btn_vote.setTextColor(getColor(btn_ok.context, R.color.colorAltoGray))
                            }
                        }
                    }
                    // 찬성 눌렀을 때
                    txt_up.id -> {
                        userSideId = topicUp?.id
                        setRes(check_up, R.drawable.ico_check_up)
                        setRes(check_down, R.drawable.ico_check)
                    }
                    // 반대 눌렀을 때
                    txt_down.id -> {
                        userSideId = topicDown?.id
                        setRes(check_up, R.drawable.ico_check)
                        setRes(check_down, R.drawable.ico_check_down)
                    }
//                    찬/반 투표하기 버튼
                    btn_vote.id -> {
                        if (userSideId != -1) { // 찬/반 선택 했는지 확인
                            postTopicVote(userSideId)
                        }
                    }
                    // 레이아웃 눌렀을때 (의견입력창 원상태로 되돌리기)
                    wrap_cont.id -> {
                        reply_off.visibility = VISIBLE
                        reply_on.visibility = GONE
                    }
                }
            }
        }

        val itemListener = object : RecyclerViewListener<View, Int, View> {
            override fun onClickItem(clickedView: View, position: Int, itemView: View) {

                val replyInfo = replies?.get(position)
                val replyId = replyInfo?.id ?: 0

                when(clickedView.id) {
//                    의견 메뉴 눌렀을때
                    btn_menu.id -> {
                        builder.let {
                            it.setItems(R.array.menu_reply) { _, which ->
                                when (which) {
                                    0 -> {
                                        deleteTopicReply(replyId, position)
                                    }
                                    1 -> {
//                                        if (topicInfo.id != -1) {
//                                            val intent = Intent(
//                                                this@DetailTopicActivity,
//                                                ReplyModifyActivity::class.java
//                                            )
//                                                .putExtra("topicId", topicInfo.id)
//                                                .putExtra("replyId", itemId)
//                                            startActivity(intent)
//                                        }
                                    }
                                }
                            }?.create()?.show()
                        }
                    }
//                    좋아요 눌렀을때
                    img_like.id -> {
                        val isLike = true
                        postTopicReplyLike(itemView, replyId, isLike)
                    }
//                    싫어요 눌렀을때
                    img_dislike.id -> {
                        val isLike = false
                        postTopicReplyLike(itemView, replyId, isLike)
                    }
//                    댓글 눌렀을때
                    num_re_reply.id -> {
                        if (replyInfo?.reply_count != 0) {
                            getTopicReply(replyId, itemView)
                        } else {
                            reReplyAdapter.list = null
                            reReplyAdapter.notifyDataSetChanged()
                        }
                    }
                }
            }
        }

        binding.list.layoutManager = LinearLayoutManager(this)
        detailTopicAdapter = DetailTopicAdapter(topicInfo, headerListener, itemListener)
        binding.list.adapter = detailTopicAdapter

        if (intent.hasExtra("topicId")) {
            getTopic(orderType, pageNum)
        } else {
            finish()
        }
    }

    private fun getTopic(orderType: String, pageNum: Int) {
        serverClient.getTopic(token, topicId.toString(), orderType, pageNum, object :
            ResultInterface<DataEntity, Boolean> {
            override fun result(value: DataEntity?, boolean: Boolean) {
                if (boolean) {
                    value?.topic?.let {

                        topicUp = it.sides?.get(0)
                        topicDown = it.sides?.get(1)
                        userSideId = it.my_side_id

                        setBookmark(it.is_my_like_topic)

                        if (it.my_side_id != -1) {
                            replyCheck = 0
                        }

                        detailTopicAdapter.topicInfo = it

                        it.replies?.run { replies?.addAll(this) }

                        detailTopicAdapter.topicInfo?.replies = replies
                        detailTopicAdapter.notifyDataSetChanged()
                    }
                }
            }
        })
    }

    private fun setListener() {
        binding.bookmark.setOnClickListener(this)
        binding.btnBack.setOnClickListener(this)
    }

    private fun postTopicReply(content: String) {
        serverClient.postTopicReply(token, topicInfo?.id, content, object :
            ResultInterface<ResponseEntity, Boolean> {
            override fun result(value: ResponseEntity?, boolean: Boolean) {
                if (boolean) {
                    getTopic(orderType, pageNum)
                } else {
                    toast("이미 의견을 작성하셨습니다.")
                }
            }
        })
    }

    private fun postTopicVote(sideId: Int?) {
        serverClient.postTopicVote(token, sideId, object :
            ResultInterface<ResponseEntity, Boolean> {
            override fun result(value: ResponseEntity?, boolean: Boolean) {
                if (boolean) {
                    value?.data?.topic?.let {
                        userSideId = it.my_side_id
                        detailTopicAdapter.topicInfo = it
                        detailTopicAdapter.notifyDataSetChanged()
                    }
                    toast("투표 성공")
                } else {
                    toast("투표를 다시 하시려면 의견을 삭제해주세요.")
                }
            }
        })
    }

    fun toast(text: String) {
        Toast.makeText(
            this,
            text,
            Toast.LENGTH_LONG
        ).show()
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.bookmark -> {
                serverClient.postTopicLike(token, topicId, object :
                    ResultInterface<ResponseEntity, Boolean> {
                    override fun result(value: ResponseEntity?, boolean: Boolean) {
                        if (boolean) {
                            val isMyLikeTopic = value?.data?.topic?.is_my_like_topic ?: run { false }
                            topicInfo?.is_my_like_topic = isMyLikeTopic
                            setBookmark(isMyLikeTopic)
                        }
                    }
                })
            }
            binding.btnBack -> {
                finish()
            }
        }
    }

    fun setBookmark(value: Boolean) {
        if (value) {
            binding.bookmark.setBackgroundResource(R.drawable.bookmark_on)
        } else {
            binding.bookmark.setBackgroundResource(R.drawable.bookmark_off)
        }
    }

    fun setRes(v: ImageView, id: Int) {
        v.setImageResource(id)
    }

    fun postTopicReplyLike(itemView: View, itemId: Int, isLike: Boolean) {
        serverClient.postTopicReplyLike(token, itemId, isLike, object :
            ResultInterface<ReplyEntity, Boolean> {
            override fun result(value: ReplyEntity?, boolean: Boolean) {
                if (boolean) {
                    value?.let {
                        itemView.num_like.text = it.like_count.toString()
                        itemView.num_dislike.text = it.dislike_count.toString()

                        // 좋아요/싫어요 누른 결과 출력
                        setRes(itemView.img_like, R.drawable.like_off)
                        setRes(itemView.img_dislike, R.drawable.like_off)

                        if (it.my_like ?: run {false}) { // my_like 가 true 일때
                            setRes(itemView.img_like, R.drawable.like_on)
                        } else if (it.my_dislike ?: run {false}) { // my_dislike 가 true 일때
                            setRes(itemView.img_dislike, R.drawable.dislike_on)
                        }
                    }
                }
            }
        })
    }

    fun deleteTopicReply(replyId: Int, position: Int) {
        serverClient.deleteTopicReply(token, replyId, object :
            ResultInterface<ResponseEntity, Boolean> {
            override fun result(
                value: ResponseEntity?,
                boolean: Boolean
            ) {
                if (boolean) {
                    replies?.removeAt(position)
                    detailTopicAdapter.topicInfo?.replies = replies
                    detailTopicAdapter.notifyDataSetChanged()
                    replyCheck = 0 // 의견을 삭제했음
                }
            }
        })
    }

    fun getTopicReply(replyId: Int, itemView: View) {
        serverClient.getTopicReply(token, replyId.toString(), object :
            ResultInterface<ResponseEntity, Boolean> {
            override fun result(
                value: ResponseEntity?,
                boolean: Boolean
            ) {
                if (boolean) {
                    val itemListener = object : RecyclerViewListener<View, Int, View> {
                        override fun onClickItem(clickedView: View, position: Int, itemView: View) {

                            val replyIds = replies?.get(position)?.id ?: 0

                            when(clickedView.id) {
//                    의견 메뉴 눌렀을때
                                btn_menu.id -> {
                                    builder.let {
                                        it.setItems(R.array.menu_reply) { _, which ->
                                            when (which) {
                                                0 -> {
                                                    deleteTopicReply(replyIds, position)
                                                }
                                                1 -> {
//                                        if (topicInfo.id != -1) {
//                                            val intent = Intent(
//                                                this@DetailTopicActivity,
//                                                ReplyModifyActivity::class.java
//                                            )
//                                                .putExtra("topicId", topicInfo.id)
//                                                .putExtra("replyId", itemId)
//                                            startActivity(intent)
//                                        }
                                                }
                                            }
                                        }?.create()?.show()
                                    }
                                }
                            }
                        }
                    }

                    itemView.list.layoutManager = LinearLayoutManager(this@DetailTopicActivity)
                    reReplyAdapter = ReReplyAdapter(value?.data?.reply?.replies, itemListener)
                    itemView.list.adapter = reReplyAdapter

                }
            }
        })
    }

}