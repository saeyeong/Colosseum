package com.sae.colosseum.view

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.sae.colosseum.R
import com.sae.colosseum.adapter.RepliesAdapter
import com.sae.colosseum.databinding.ActivityTopicBinding
import com.sae.colosseum.interfaces.RecyclerViewListener
import com.sae.colosseum.model.entity.DataEntity
import com.sae.colosseum.model.entity.RepliesEntity
import com.sae.colosseum.model.entity.ResponseEntity
import com.sae.colosseum.model.entity.TopicInfoEntity
import com.sae.colosseum.network.ServerClient
import com.sae.colosseum.utils.GlobalApplication
import com.sae.colosseum.utils.GlobalApplication.Companion.userNickname
import com.sae.colosseum.utils.ResultInterface
import kotlinx.android.synthetic.main.activity_topic.*
import kotlinx.android.synthetic.main.item_reply.*
import kotlinx.android.synthetic.main.item_reply.view.*

class TopicActivity : AppCompatActivity(), View.OnClickListener, TextWatcher {

    private lateinit var binding: ActivityTopicBinding
    var tag: String? = null
    var serverClient: ServerClient? = null
    var token: String? = null
    private var topicId: Int? = null
    var topicInfo: TopicInfoEntity? = null
    var upId: Int? = null
    var downId: Int? = null
    var vote: Int = -1 // 투표를 했는지 안했는지
    var reply: Int = -1 // 의견을 작성했는지 안했는지
    lateinit var recyclerListener: RecyclerViewListener<RepliesEntity, View>
    lateinit var adapter: RepliesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_topic)
        init()
    }

    override fun onClick(v: View?) {
        val sideId: Int
        val content: String

        when (v) {
            // 댓글 영역 눌렀을 때
            binding.commentOff -> {
                v.visibility = GONE
                binding.commentOn.visibility = VISIBLE
            }
            // 댓글 등록하기 눌렀을 때
            binding.btnOk -> {
                binding.commentOn.visibility = GONE
                binding.commentOff.visibility = VISIBLE

                content = binding.editCmt.text.toString()
                topicReply(content)

            }
            // 찬성 눌렀을 때
            binding.upWrap -> {
                sideId = upId ?: 0
                if(sideId != 0) { // upId가 잘 들어왔는지 확인
                    topicVote(sideId)
                }
            }
            // 반대 눌렀을 때
            binding.downWrap -> {
                sideId = downId ?: 0
                if(sideId != 0) {
                    topicVote(sideId)
                    if(reply == 0) {
                        vote = 0
                        clickDownColor()
                    }
                }
            }
            // 의견 전체보기 눌렀을 때
            binding.btnViewAllReply -> {
                val intent = Intent(this, ReplyActivity::class.java)
                startActivity(intent)
            }
        }
    }

    fun init() {
        serverClient = ServerClient()
        setListener()
        binding.txtNickname.text = userNickname
        token = GlobalApplication.prefs.myEditText
        topicId = intent.getIntExtra("topicId", -1)

        if (intent.hasExtra("topicId")) {
            setData()
        } else {
            finish()
        }

        recyclerListener = object : RecyclerViewListener<RepliesEntity, View> {
            override fun onClickItemForViewId(item: RepliesEntity, clickedView: View, itemReplyView: View) {
                var isLike: Boolean = false
                when (clickedView.id) { // 좋아요/싫어요 입력
                    like_wrap.id -> { // 좋아요 눌렀을 때
                        isLike = true
                    }
                    dislike_wrap.id -> { // 싫어요 눌렀을 때
                        isLike = false
                    }
                }
                serverClient?.postTopicReplyLike(token, item.id, isLike, object : ResultInterface<RepliesEntity> {
                    override fun result(value: RepliesEntity) {
                        itemReplyView.num_like.text = value.like_count.toString()
                        itemReplyView.num_dislike.text = value.dislike_count.toString()

                        // 좋아요/싫어요 누른 결과 출력
                        if(value.my_like) { // my_like 가 true 일때
                            itemReplyView.img_like.setImageResource(R.drawable.like_on)
                            itemReplyView.img_dislike.setImageResource(R.drawable.dislike_off)
                        } else if(value.my_dislike) { // my_dislike 가 true 일때
                            itemReplyView.img_dislike.setImageResource(R.drawable.dislike_on)
                            itemReplyView.img_like.setImageResource(R.drawable.like_off)
                        } else { // 둘다 false 일때
                            itemReplyView.img_dislike.setImageResource(R.drawable.dislike_off)
                            itemReplyView.img_like.setImageResource(R.drawable.like_off)
                        }
                    }
                })
            }
        }
    }

    private fun setListener() {
        binding.bookmark.setOnClickListener(this)
        binding.commentOff.setOnClickListener(this)
        binding.btnOk.setOnClickListener(this)
        binding.editCmt.addTextChangedListener(this)
        binding.upWrap.setOnClickListener(this)
        binding.downWrap.setOnClickListener(this)
        binding.btnViewAllReply.setOnClickListener(this)
    }

    private fun setData() {

        serverClient?.getTopic(token, topicId, object : ResultInterface<DataEntity> {
            override fun result(value: DataEntity) {
                topicInfo = value.topic

                topicInfo?.let {
                    txt_title.text = it.title
                    cmt_num_topic.text = it.reply_count.toString()
                    txt_start_data.text = it.start_date
                    num_up.text = it.sides[0].vote_count.toString()
                    num_down.text = it.sides[1].vote_count.toString()
                    num_cmt.text = it.reply_count.toString()

                    if (it.my_side_id == it.sides[0].id) {
                        clickUpColor()
                    } else if (it.my_side_id == it.sides[1].id) {
                        clickDownColor()
                    } else {
                        reply = 0
                    }
                    upId = it.sides[0].id
                    downId = it.sides[1].id

                    adapter = RepliesAdapter(this@TopicActivity, it.replies, recyclerListener)
                    binding.listItem.adapter = adapter
                    binding.listItem.layoutManager = LinearLayoutManager(this@TopicActivity)

                }
            }
        })
    }

    private fun topicReply(content: String) {
        serverClient?.postTopicReply(token, topicId, content, object : ResultInterface<Boolean> {
            override fun result(value: Boolean) {
                if(!value) {
                    Toast.makeText(
                        this@TopicActivity,
                        "이미 의견을 작성하셨습니다.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })
    }

    private fun topicVote(sideId: Int?) {
        serverClient?.postTopicVote(token, sideId, object : ResultInterface<Boolean> {
            override fun result(value: Boolean) {
                    if (value) {
                        vote = 0
                    } else {
                        Toast.makeText(
                            this@TopicActivity,
                            "투표를 변경하시려면 의견을 삭제해주시기 바랍니다.",
                            Toast.LENGTH_LONG
                        ).show()
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
        binding.txtUp.setTextColor(ContextCompat.getColor(applicationContext, R.color.colorUp))
        binding.numUp.setTextColor(ContextCompat.getColor(applicationContext, R.color.colorUp))
        binding.txtDown.setTextColor(ContextCompat.getColor(applicationContext, android.R.color.tab_indicator_text))
        binding.numDown.setTextColor(ContextCompat.getColor(applicationContext, android.R.color.tab_indicator_text))
    }

    private fun clickDownColor() {
        binding.txtUp.setTextColor(ContextCompat.getColor(applicationContext, android.R.color.tab_indicator_text))
        binding.numUp.setTextColor(ContextCompat.getColor(applicationContext, android.R.color.tab_indicator_text))
        binding.txtDown.setTextColor(ContextCompat.getColor(applicationContext, R.color.colorDown))
        binding.numDown.setTextColor(ContextCompat.getColor(applicationContext, R.color.colorDown))
    }

}