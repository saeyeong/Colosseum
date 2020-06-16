package com.sae.colosseum.view

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.sae.colosseum.R
import com.sae.colosseum.adapter.ReplyAdapter
import com.sae.colosseum.databinding.ActivityTopicBinding
import com.sae.colosseum.interfaces.RecyclerViewListener
import com.sae.colosseum.model.entity.DataEntity
import com.sae.colosseum.model.entity.RepliesEntity
import com.sae.colosseum.model.entity.ResponseEntity
import com.sae.colosseum.model.entity.TopicInfoEntity
import com.sae.colosseum.utils.BaseActivity
import com.sae.colosseum.utils.GlobalApplication
import com.sae.colosseum.utils.ResultInterface
import kotlinx.android.synthetic.main.activity_topic.*
import kotlinx.android.synthetic.main.item_reply.*
import kotlinx.android.synthetic.main.item_reply.view.*

class TopicActivity : BaseActivity(), View.OnClickListener, TextWatcher {

    private lateinit var binding: ActivityTopicBinding
    var mIntent: Intent? = null
    var topicInfo: TopicInfoEntity? = null
    var upId: Int? = null
    var downId: Int? = null
    var mySideId: Int? = null // 유저 찬/반 정보
    var replyCheck: Int = -1 // 의견 작성 확인
    var builder: AlertDialog.Builder? = null
    private var topicId: Int = -1
    lateinit var recyclerListener: RecyclerViewListener<RepliesEntity, View>
    lateinit var adapter: ReplyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_topic)
        init()
    }

    override fun onResume() {
        super.onResume()
        setData()
    }

    override fun onClick(v: View?) {
        val content: String

        when (v) {
            // 의견 영역 눌렀을 때
            binding.replyOff -> {
                v.visibility = GONE
                binding.replyOn.visibility = VISIBLE
            }
            // 의견 등록하기 눌렀을 때
            binding.btnOk -> {
                if(mySideId == -1) { // 투표 검사
                    Toast.makeText(this, "투표를 완료하시면 의견을 작성하실 수 있습니다", Toast.LENGTH_LONG).show()
                } else {
                    binding.replyOn.visibility = GONE
                    binding.replyOff.visibility = VISIBLE

                    content = binding.editReply.text.toString()
                    postTopicReply(content, null)

                    replyCheck = 0 // 의견을 작성했기때문에 투표를 바꿀 수 없음
                }
            }
            // 찬성 눌렀을 때
            binding.upWrap -> {
                if(replyCheck != 0) { // 의견을 작성했는지 확인 (의견을 작성한 상태면 투표를 바꿀 수 없음)
                    postTopicVote(upId)
                } else {
                    toast("진영을 변경하시려면 의견을 삭제해 주세요.")
                }
            }
            // 반대 눌렀을 때
            binding.downWrap -> {
                if(replyCheck != 0) { // 의견을 작성했는지 확인
                    postTopicVote(downId)
                } else {
                    toast("진영을 변경하시려면 의견을 삭제해 주세요.")
                }
            }
            // 의견 전체보기 눌렀을 때
            binding.btnGoReply -> {
                val intent = Intent(this, ReplyActivity::class.java)
                startActivity(intent)
            }
            // 레이아웃 눌렀을때 (의견입력창 원상태로 되돌리기)
            binding.topicWrap -> {
                binding.replyOff.visibility = VISIBLE
                binding.replyOn.visibility = GONE
            }
        }
    }

    fun init() {
        setListener()
        builder = AlertDialog.Builder(this)
        binding.txtNickname.text = GlobalApplication.loginUser.nick_name
        topicId = intent.getIntExtra("topicId", -1)


        if (intent.hasExtra("topicId")) {
            setData()
        } else {
            finish()
        }

        recyclerListener = object : RecyclerViewListener<RepliesEntity, View> {
            override fun onClickItemForViewId(item: RepliesEntity, clickedView: View, itemReplyView: View) {

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
                                        mIntent = Intent(this@TopicActivity, ReplyModifyActivity::class.java)
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
                            itemReplyView.num_like.text = value.like_count.toString()
                            itemReplyView.num_dislike.text = value.dislike_count.toString()

                            // 좋아요/싫어요 누른 결과 출력
                            if (value.my_like!!) { // my_like 가 true 일때
                                itemReplyView.img_like.setImageResource(R.drawable.like_on)
                                itemReplyView.img_dislike.setImageResource(R.drawable.dislike_off)
                            } else if (value.my_dislike!!) { // my_dislike 가 true 일때
                                itemReplyView.img_dislike.setImageResource(R.drawable.dislike_on)
                                itemReplyView.img_like.setImageResource(R.drawable.like_off)
                            } else { // 둘다 false 일때
                                itemReplyView.img_dislike.setImageResource(R.drawable.dislike_off)
                                itemReplyView.img_like.setImageResource(R.drawable.like_off)
                            }
                        }
                    })
                } else {
                    mIntent = Intent(this@TopicActivity, ReReplyActivity::class.java)
                        .putExtra("topicId", topicId)
                        .putExtra("replyId", item.id)
                        .putExtra("replyObject", item)
                    startActivity(mIntent)
                }
            }
        }
    }

    private fun setListener() {
        binding.bookmark.setOnClickListener(this)
        binding.replyOff.setOnClickListener(this)
        binding.btnOk.setOnClickListener(this)
        binding.editReply.addTextChangedListener(this)
        binding.upWrap.setOnClickListener(this)
        binding.downWrap.setOnClickListener(this)
        binding.btnGoReply.setOnClickListener(this)
        binding.topicWrap.setOnClickListener(this)
    }

    private fun setData() {
        serverClient.getTopic(token, topicId.toString(), object : ResultInterface<DataEntity> {
            override fun result(value: DataEntity) {
                topicInfo = value.topic

                topicInfo?.let {
                    txt_title.text = it.title
                    txt_start_data.text = it.start_date
                    num_up.text = it.sides[0].vote_count.toString()
                    num_down.text = it.sides[1].vote_count.toString()
                    num_reply.text = it.reply_count.toString()

                    upId = it.sides[0].id // 찬성 아이디 초기화
                    downId = it.sides[1].id // 반대 아이디 초기화
                    mySideId = it.my_side_id // 내 찬/반 정보 초기화

//                    유저 찬/반 정보에 따라 버튼 색 초기화
                    if (mySideId == upId) {
                        clickUpColor()
                    } else if (mySideId == downId) {
                        clickDownColor()
                    } else {
                        noneColor()
                    }

                    adapter = ReplyAdapter(it, recyclerListener)
                    binding.listItem.adapter = adapter
                    binding.listItem.layoutManager = LinearLayoutManager(this@TopicActivity)

                    Glide.with(this@TopicActivity).load(it.img_url).into(binding.imgTopic)
                }
            }
        })
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

    override fun afterTextChanged(s: Editable?) {
        binding.btnOk.isEnabled = (s?.length ?: 0) > 5
        binding.numCharacters.text = s?.length.toString()
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

    private fun clickUpColor() {
        binding.txtUp.setTextColor(ContextCompat.getColor(this, R.color.colorUp))
        binding.numUp.setTextColor(ContextCompat.getColor(this, R.color.colorUp))
        binding.txtDown.setTextColor(ContextCompat.getColor(this, android.R.color.tab_indicator_text))
        binding.numDown.setTextColor(ContextCompat.getColor(this, android.R.color.tab_indicator_text))
    }

    private fun clickDownColor() {
        binding.txtUp.setTextColor(ContextCompat.getColor(this, android.R.color.tab_indicator_text))
        binding.numUp.setTextColor(ContextCompat.getColor(this, android.R.color.tab_indicator_text))
        binding.txtDown.setTextColor(ContextCompat.getColor(this, R.color.colorDown))
        binding.numDown.setTextColor(ContextCompat.getColor(this, R.color.colorDown))
    }

    private fun noneColor() {
        binding.txtUp.setTextColor(ContextCompat.getColor(this, android.R.color.tab_indicator_text))
        binding.numUp.setTextColor(ContextCompat.getColor(this, android.R.color.tab_indicator_text))
        binding.txtDown.setTextColor(ContextCompat.getColor(this, android.R.color.tab_indicator_text))
        binding.numDown.setTextColor(ContextCompat.getColor(this, android.R.color.tab_indicator_text))
    }

    fun toast(tText: String) {
        Toast.makeText(
            this,
            tText,
            Toast.LENGTH_LONG
        ).show()
    }

}