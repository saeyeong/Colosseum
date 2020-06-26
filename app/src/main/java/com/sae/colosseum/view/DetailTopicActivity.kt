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
import com.sae.colosseum.databinding.ActivityTopicDetailBinding
import com.sae.colosseum.interfaces.RecyclerViewListener
import com.sae.colosseum.model.entity.DataEntity
import com.sae.colosseum.model.entity.ReplyEntity
import com.sae.colosseum.model.entity.ResponseEntity
import com.sae.colosseum.utils.BaseActivity
import com.sae.colosseum.interfaces.ResultInterface
import com.sae.colosseum.model.entity.TopicInfoEntity
import kotlinx.android.synthetic.main.header_topic.*
import kotlinx.android.synthetic.main.item_reply.*
import kotlinx.android.synthetic.main.item_reply.view.*

class DetailTopicActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityTopicDetailBinding
    lateinit var adapter: DetailTopicAdapter
    lateinit var topicInfo: TopicInfoEntity
    var replyCheck: Int = 1 // 0 : 쓸수있음 1 : 이미 썼음
    var orderType: String = "NEW"
    var pageNum: Int = 1
    var replies: ArrayList<ReplyEntity>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_topic_detail)
        init()
    }

    override fun onResume() {
        super.onResume()
        setData(orderType, pageNum)
    }

    fun init() {
        setListener()

        val builder = AlertDialog.Builder(this)
        topicInfo = TopicInfoEntity()
        topicInfo.id = intent.getIntExtra("topicId", -1)
        replies = ArrayList()

        binding.list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    setData(orderType, pageNum)
                }
            }

        })

        val headerListener = object : RecyclerViewListener<ReplyEntity, View> {
            override fun onClickItem(item: ReplyEntity, clickedView: View, itemView: View) {
                val content: String
                when (clickedView.id) {
                    // 의견 영역 눌렀을 때
                    edit_wrap.id -> {
                        reply_off.visibility = GONE
                        reply_on.visibility = VISIBLE
                    }
                    // 의견 등록하기 눌렀을 때
                    btn_ok.id -> {
                        if (topicInfo.my_side_id == -1) { // 투표 검사
                            toast("투표를 완료하시면 의견을 작성하실 수 있습니다")
                        } else if (replyCheck == 1) {
                            toast("의견을 이미 작성하셨습니다.")
                        } else {
                            reply_on.visibility = GONE
                            reply_off.visibility = VISIBLE

                            content = edit_reply.text.toString()
                            postTopicReply(content)

                            replyCheck = 1

                            btn_vote.isEnabled = false
                            btn_vote.setTextColor(getColor(btn_ok.context, R.color.colorNeutralGray))
                        }
                    }
                    // 찬성 눌렀을 때
                    wrap_up.id -> {
                        topicInfo.my_side_id = topicInfo.sides[0].id
                        setRes(check_up, R.drawable.ico_check_up)
                        setRes(check_down, R.drawable.ico_check)
                    }
                    // 반대 눌렀을 때
                    wrap_down.id -> {
                        topicInfo.my_side_id = topicInfo.sides[1].id
                        setRes(check_up, R.drawable.ico_check)
                        setRes(check_down, R.drawable.ico_check_down)
                    }
//                    찬/반 투표하기 버튼
                    btn_vote.id -> {
                        if (topicInfo.my_side_id != -1) { // 찬/반 선택 했는지 확인
                            postTopicVote(topicInfo.my_side_id)
                        }
                    }
                    // 레이아웃 눌렀을때 (의견입력창 원상태로 되돌리기)
                    wrap_topic.id -> {
                        reply_off.visibility = VISIBLE
                        reply_on.visibility = GONE
                    }
                }
            }
        }

        val itemListener = object : RecyclerViewListener<ReplyEntity, View> {
            override fun onClickItem(item: ReplyEntity, clickedView: View, itemView: View) {
                if (clickedView.id == btn_menu.id) {
//                의견 메뉴 눌렀을때
                    builder?.let {
                        it.setItems(
                            R.array.menu_reply
                        ) { dialog, which ->
                            when (which) {
                                0 -> {
                                    serverClient.deleteTopicReply(token, item.id, object :
                                        ResultInterface<ResponseEntity, Boolean> {
                                        override fun result(
                                            value: ResponseEntity?,
                                            boolean: Boolean
                                        ) {
                                            if (boolean) {
                                                setData(orderType, pageNum)
                                                replyCheck = 0 // 의견을 삭제했음
                                            }
                                        }
                                    })
                                }
                                1 -> {
                                    if (topicInfo.id != -1) {
                                        val intent = Intent(
                                            this@DetailTopicActivity,
                                            ReplyModifyActivity::class.java
                                        )
                                            .putExtra("topicId", topicInfo.id)
                                            .putExtra("replyId", item.id)
                                        startActivity(intent)
                                    }
                                }
                            }
                        }?.create()?.show()
                    }
                } else if (clickedView.id == like_wrap.id || clickedView.id == dislike_wrap.id) {
//                    좋아요/싫어요 눌렀을때
                    var isLike = false
                    if (clickedView.id == like_wrap.id) isLike = true
                    else if (clickedView.id == dislike_wrap.id) isLike = false

                    serverClient.postTopicReplyLike(token, item.id, isLike, object :
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
            }
        }

        binding.list.layoutManager = LinearLayoutManager(this)

        adapter = DetailTopicAdapter(topicInfo, headerListener, itemListener)
        binding.list.adapter = adapter

        if (intent.hasExtra("topicId")) {
            setData(orderType, pageNum)
        } else {
            finish()
        }


    }

    private fun setData(orderType: String, pageNum: Int) {
        serverClient.getTopic(token, topicInfo.id.toString(), orderType, pageNum, object :
            ResultInterface<DataEntity, Boolean> {
            override fun result(value: DataEntity?, boolean: Boolean) {
                if (boolean) {
                    value?.let {
                        topicInfo = it.topic
                        setBookmark(topicInfo.is_my_like_topic)

                        if (topicInfo.my_side_id != -1) {
                            replyCheck = 0
                        } else {
                            btn_vote.isEnabled = false
                            btn_vote.setTextColor(getColor(btn_ok.context, R.color.colorNeutralGray))
                        }



                        adapter.topicInfo = topicInfo

                        topicInfo.replies?.run { replies?.addAll(this) }

                        adapter.topicInfo?.replies = replies

                        adapter.notifyDataSetChanged()

                        this@DetailTopicActivity.pageNum += 1
                    }
                } else {
                    Log.d("test", "토픽목록불러오기실패")
                }
            }
        })
    }

    private fun setListener() {
        binding.bookmark.setOnClickListener(this)
        binding.btnBack.setOnClickListener(this)
    }

    private fun postTopicReply(content: String) {
        serverClient.postTopicReply(token, topicInfo.id, content, object :
            ResultInterface<ResponseEntity, Boolean> {
            override fun result(value: ResponseEntity?, boolean: Boolean) {
                if (boolean) {
                    setData(orderType, pageNum)
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
                    value?.let {
                        topicInfo.my_side_id = it.data.topic.my_side_id

//                    유저 찬/반 정보에 따라 버튼 색 초기화
                        setRes(check_up, R.drawable.ico_check)
                        setRes(check_down, R.drawable.ico_check)

                        if (topicInfo.my_side_id == topicInfo.sides[0].id) {
                            setRes(check_up, R.drawable.ico_check_up)
                        } else if (topicInfo.my_side_id == topicInfo.sides[1].id) {
                            setRes(check_down, R.drawable.ico_check_down)
                        }
                    }
                    toast("투표 성공")
                } else {
                    toast("postTopicVote 실패")
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
                serverClient.postTopicLike(token, topicInfo.id, object :
                    ResultInterface<ResponseEntity, Boolean> {
                    override fun result(value: ResponseEntity?, boolean: Boolean) {
                        if (boolean) {
                            value?.let {
                                topicInfo.is_my_like_topic = it.data.topic.is_my_like_topic
                                setBookmark(topicInfo.is_my_like_topic)
                            }
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

}