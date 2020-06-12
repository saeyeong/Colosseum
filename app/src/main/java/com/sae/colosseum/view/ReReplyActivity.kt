package com.sae.colosseum.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.sae.colosseum.R
import com.sae.colosseum.adapter.ReReplyAdapter
import com.sae.colosseum.databinding.ActivityReReplyBinding
import com.sae.colosseum.model.entity.RepliesEntity
import com.sae.colosseum.model.entity.ResponseEntity
import com.sae.colosseum.utils.BaseActivity
import com.sae.colosseum.utils.ResultInterface

class ReReplyActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityReReplyBinding
    var topicId: Int = -1
    var replyId: Int = -1
    var replyObject: RepliesEntity? = null
    lateinit var adapter: ReReplyAdapter
    var topicInfo: RepliesEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_re_reply)

        init()
    }

    fun init() {
        topicId = intent.getIntExtra("topicId", -1)
        replyId = intent.getIntExtra("replyId", -1)
        replyObject = intent.getParcelableExtra("replyObject")

        binding.nickname.text = replyObject?.user?.nick_name.toString()
        binding.content.text = replyObject?.content.toString()
        binding.txtDate.text = replyObject?.updated_at.toString()

        serverClient.getTopicReReply(token, replyId.toString(), object : ResultInterface<ResponseEntity> {
            override fun result(value: ResponseEntity) {
                topicInfo = value.data.reply

                topicInfo?.let {
                    adapter = ReReplyAdapter(it.replies)
                    binding.listReReply.adapter = adapter
                    binding.listReReply.layoutManager = LinearLayoutManager(this@ReReplyActivity)
                }
            }
        })

        setListener()
    }

    private fun setListener() {
        binding.btnBack.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        finish()
    }
}
