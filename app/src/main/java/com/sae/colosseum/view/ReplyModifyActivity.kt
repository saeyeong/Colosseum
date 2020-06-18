package com.sae.colosseum.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import com.sae.colosseum.R
import com.sae.colosseum.databinding.ActivityReplyModifyBinding
import com.sae.colosseum.model.entity.ResponseEntity
import com.sae.colosseum.utils.BaseActivity
import com.sae.colosseum.utils.ResultInterface

class ReplyModifyActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityReplyModifyBinding
    var topicId: Int = -1
    var replyId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reply_modify)

        init()
    }

    fun init() {
        topicId = intent.getIntExtra("topicId", -1)
        replyId = intent.getIntExtra("replyId", -1)

        setListener()
    }

    private fun setListener() {
        binding.btnOk.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val content = binding.editReply.text.toString()

        serverClient.putTopicReply(token, replyId, content, object : ResultInterface<ResponseEntity> {
            override fun result(value: ResponseEntity) {

                Log.d("test",topicId.toString())
                if(topicId != -1 || replyId != -1) {

                    Log.d("test",topicId.toString())
                    val mIntent = Intent(this@ReplyModifyActivity, DetailTopicActivity::class.java).putExtra("topicId", topicId)
                    startActivity(mIntent)
                }
            }
        })
    }
}
