package com.sae.colosseum.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.sae.colosseum.R
import com.sae.colosseum.adapter.UserReplyAdapter
import com.sae.colosseum.databinding.FragmentUserReplyBinding
import com.sae.colosseum.network.ServerClient
import com.sae.colosseum.interfaces.ResultInterface
import com.sae.colosseum.model.entity.ReplyEntity
import com.sae.colosseum.model.entity.ResponseEntity
import com.sae.colosseum.utils.GlobalApplication

class UserReplyFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentUserReplyBinding
    var serverClient: ServerClient? = null
    var token: String? = null
    lateinit var adapter: UserReplyAdapter
    var myReplies: ArrayList<ReplyEntity>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_user_reply,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    fun init() {
        setListener()
        token = GlobalApplication.prefs.userToken
        serverClient = ServerClient()
        binding.list.layoutManager = LinearLayoutManager(context)

        adapter = UserReplyAdapter(myReplies)
        binding.list.adapter = adapter
        setData()
    }

    private fun setListener() {
        binding.btnBack.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v) {
            binding.btnBack -> {
                fragmentManager?.run {
                    beginTransaction().remove(this@UserReplyFragment).commit()
                    popBackStack()
                }
            }
        }
    }

    private fun setData() {
        serverClient?.getUserInfo(token, 1, object :
            ResultInterface<ResponseEntity, Boolean> {
            override fun result(value: ResponseEntity?, boolean: Boolean) {
                if(boolean) {
                    val numReply = value?.data?.my_replies_count.toString()
                    binding.messageNumUserReply.text = getString(R.string.ko_message_user_reply, numReply)
                    adapter.list = value?.data?.my_replies
                    adapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(context, "정보 불러오기에 실패했습니다.", Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    companion object{
        fun newInstance(): Fragment{
            val args = Bundle()

            val fragment = UserReplyFragment()
            fragment.arguments = args
            return fragment
        }
    }
}