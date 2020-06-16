package com.sae.colosseum.view

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.sae.colosseum.R
import com.sae.colosseum.adapter.ReReplyAdapter
import com.sae.colosseum.databinding.ActivityReReplyBinding
import com.sae.colosseum.interfaces.RecyclerViewListener
import com.sae.colosseum.model.entity.RepliesEntity
import com.sae.colosseum.model.entity.ResponseEntity
import com.sae.colosseum.utils.BaseActivity
import com.sae.colosseum.utils.ResultInterface

class ReReplyActivity : BaseActivity(), View.OnClickListener, TextWatcher {
    private lateinit var binding: ActivityReReplyBinding
    var topicId: Int = -1
    var replyId: Int = -1
    var replyObject: RepliesEntity? = null
    lateinit var adapter: ReReplyAdapter
    lateinit var recyclerListener: RecyclerViewListener<RepliesEntity, View>
    var builder: AlertDialog.Builder? = null
    var mIntent: Intent? = null

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
        
        builder = AlertDialog.Builder(this)
        setData()
        setListener()
        recyclerListener = object : RecyclerViewListener<RepliesEntity, View> {
            override fun onClickItemForViewId(item: RepliesEntity, clickedView: View, itemReplyView: View) {
                builder?.let {
                    it.setItems(R.array.menu_reply
                    ) { dialog, which ->
                        when(which) {
                            0 -> {
                                serverClient.deleteTopicReply(token, item.id, object : ResultInterface<ResponseEntity> {
                                    override fun result(value: ResponseEntity) {
                                        setData()
                                    }
                                })
                            }
                            1 -> {
                                if(topicId != -1) {
                                    mIntent = Intent(this@ReReplyActivity, ReplyModifyActivity::class.java)
                                        .putExtra("topicId", topicId)
                                        .putExtra("replyId", item.id)
                                    startActivity(mIntent)
                                }
                            }
                        }
                    }?.create()?.show()
                }
            }
        }
    }

    private fun setListener() {
        binding.btnBack.setOnClickListener(this)
        binding.reReplyOff.setOnClickListener(this)
        binding.btnOk.setOnClickListener(this)
        binding.editReReply.addTextChangedListener(this)
    }

    override fun onClick(v: View?) {
        val content: String
        when(v) {
            binding.btnBack -> {
                finish()
            }
            binding.reReplyOff -> {
                v.visibility = View.GONE
                binding.reReplyOn.visibility = View.VISIBLE
            }
            binding.btnOk -> {
                binding.reReplyOn.visibility = View.GONE
                binding.reReplyOff.visibility = View.VISIBLE

                content = binding.editReReply.text.toString()
                topicReply(content)
                setData()
            }
        }

    }

    private fun topicReply(content: String) {
        serverClient.postTopicReply(token, null, content, replyId, object : ResultInterface<Boolean> {
            override fun result(value: Boolean) {
                if(value) {
                    setData()
                } else {
                    Toast.makeText(
                        this@ReReplyActivity,
                        "댓글 등록에 실패했습니다.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })
    }

    private fun setData() {
        serverClient.getTopicReReply(token, replyId.toString(), object : ResultInterface<ResponseEntity> {
            override fun result(value: ResponseEntity) {
                binding.txtNickname.text = value.data.reply.user?.nick_name ?: ""

                value.data.reply.let {
                    adapter = ReReplyAdapter(it.replies, recyclerListener)
                    binding.listReReply.adapter = adapter
                    binding.listReReply.layoutManager = LinearLayoutManager(this@ReReplyActivity)
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
}
