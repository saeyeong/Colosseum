package com.sae.colosseum.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.sae.colosseum.R
import com.sae.colosseum.adapter.DetailTopicAdapter
import com.sae.colosseum.databinding.ActivityTopicDetailBinding
import com.sae.colosseum.interfaces.RecyclerViewListener
import com.sae.colosseum.model.entity.DataEntity
import com.sae.colosseum.model.entity.RepliesEntity
import com.sae.colosseum.model.entity.ResponseEntity
import com.sae.colosseum.utils.BaseActivity
import com.sae.colosseum.utils.ResultInterface
import kotlinx.android.synthetic.main.header_topic.*
import kotlinx.android.synthetic.main.item_reply.*
import kotlinx.android.synthetic.main.item_reply.view.*

class DetailTopicActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityTopicDetailBinding
    var mIntent: Intent? = null
    var upId: Int? = null
    var downId: Int? = null
    var mySideId: Int? = null // 유저 찬/반 정보
    var replyCheck: Int = -1 // 의견 작성 확인
    var builder: AlertDialog.Builder? = null
    private var topicId: Int = -1
    lateinit var headerListener: RecyclerViewListener<RepliesEntity, View>
    private lateinit var itemListener: RecyclerViewListener<RepliesEntity, View>
    lateinit var adapter: DetailTopicAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_topic_detail)
        init()
    }

    override fun onResume() {
        super.onResume()
        setData()
    }

    fun init() {
        setListener()
        builder = AlertDialog.Builder(this)
        topicId = intent.getIntExtra("topicId", -1)

        if (intent.hasExtra("topicId")) {
            setData()
        } else {
            finish()
        }

        headerListener = object : RecyclerViewListener<RepliesEntity, View> {
            override fun onClickItem(position: RepliesEntity, clickedView: View, itemView: View) {
                val content: String
                when (clickedView.id) {
                    // 의견 영역 눌렀을 때
                    edit_wrap.id -> {
                        reply_off.visibility = GONE
                        reply_on.visibility = VISIBLE
                    }
                    // 의견 등록하기 눌렀을 때
                    btn_ok.id -> {
                        if(mySideId == -1) { // 투표 검사
                            Toast.makeText(this@DetailTopicActivity, "투표를 완료하시면 의견을 작성하실 수 있습니다", Toast.LENGTH_LONG).show()
                        } else {
                            reply_on.visibility = GONE
                            reply_off.visibility = VISIBLE

                            content = edit_reply.text.toString()
                            postTopicReply(content, null)

                            replyCheck = 0 // 의견을 작성했기때문에 투표를 바꿀 수 없음
                        }
                    }
                    // 찬성 눌렀을 때
                    up_wrap.id -> {
                        if(replyCheck != 0) { // 의견을 작성했는지 확인 (의견을 작성한 상태면 투표를 바꿀 수 없음)
                            postTopicVote(upId)
                        } else {
                            toast("진영을 변경하시려면 의견을 삭제해 주세요.")
                        }
                    }
                    // 반대 눌렀을 때
                    down_wrap.id -> {
                        if(replyCheck != 0) { // 의견을 작성했는지 확인
                            postTopicVote(downId)
                        } else {
                            toast("진영을 변경하시려면 의견을 삭제해 주세요.")
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

        itemListener = object : RecyclerViewListener<RepliesEntity, View> {
            override fun onClickItem(item: RepliesEntity, clickedView: View, itemView: View) {
                if (clickedView.id == btn_menu.id) {
//                의견 메뉴 눌렀을때
                    builder?.let {
                        it.setItems(R.array.menu_reply
                        ) { dialog, which ->
                            when(which) {
                                0 -> {
                                    serverClient.deleteTopicReply(token, item.id, object : ResultInterface<ResponseEntity> {
                                        override fun result(value: ResponseEntity) {
                                            setData()
                                            replyCheck = -1 // 의견을 삭제했음
                                        }
                                    })
                                }
                                1 -> {
                                    if(topicId != -1) {
                                        mIntent = Intent(this@DetailTopicActivity, ReplyModifyActivity::class.java)
                                            .putExtra("topicId", topicId)
                                            .putExtra("replyId", item.id)
                                        startActivity(mIntent)
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

                    serverClient.postTopicReplyLike(token, item.id, isLike, object : ResultInterface<RepliesEntity> {
                        override fun result(value: RepliesEntity) {
                            itemView.num_like.text = value.like_count.toString()
                            itemView.num_dislike.text = value.dislike_count.toString()

                            // 좋아요/싫어요 누른 결과 출력
                            if (value.my_like!!) { // my_like 가 true 일때
                                itemView.img_like.setImageResource(R.drawable.like_on)
                                itemView.img_dislike.setImageResource(R.drawable.dislike_off)
                            } else if (value.my_dislike!!) { // my_dislike 가 true 일때
                                itemView.img_dislike.setImageResource(R.drawable.dislike_on)
                                itemView.img_like.setImageResource(R.drawable.like_off)
                            } else { // 둘다 false 일때
                                itemView.img_dislike.setImageResource(R.drawable.dislike_off)
                                itemView.img_like.setImageResource(R.drawable.like_off)
                            }
                        }
                    })
                } else {
                    mIntent = Intent(this@DetailTopicActivity, ReReplyActivity::class.java)
                        .putExtra("topicId", topicId)
                        .putExtra("replyId", item.id)
                        .putExtra("replyObject", item)
                    startActivity(mIntent)
                }
            }
        }
    }

    private fun setData() {
        serverClient.getTopic(token, topicId.toString(), object : ResultInterface<DataEntity> {
            override fun result(value: DataEntity) {
                upId = value.topic.sides[0].id
                downId = value.topic.sides[1].id

                adapter = DetailTopicAdapter(value, headerListener, itemListener)
                binding.recyclerView.adapter = adapter
                binding.recyclerView.layoutManager = LinearLayoutManager(this@DetailTopicActivity)
            }
        })
    }

    private fun setListener() {
        binding.bookmark.setOnClickListener(this)
    }

    private fun postTopicReply(content: String, parentReplyId: Int?) {
        serverClient.postTopicReply(token, topicId, content, parentReplyId, object : ResultInterface<Boolean> {
            override fun result(value: Boolean) {
                if(value) {
                    setData()
                } else {
                    toast("이미 의견을 작성하셨습니다.")
                }
            }
        })
    }

    private fun postTopicVote(sideId: Int?) {
        serverClient.postTopicVote(token, sideId, object : ResultInterface<Boolean> {
            override fun result(value: Boolean) {
                if (value) {
                    setData()
                } else {
                    toast("투표를 변경하시려면 의견을 삭제해주시기 바랍니다.")
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
        when(v) {
            binding.bookmark -> {
                serverClient.postTopicLike(token, topicId, object : ResultInterface<Boolean> {
                    override fun result(value: Boolean) {
                        if(value) {
                        } else {
                        }
                    }
                })
            }
        }
    }

}