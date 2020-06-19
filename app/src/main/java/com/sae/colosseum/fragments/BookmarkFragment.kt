package com.sae.colosseum.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.sae.colosseum.R
import com.sae.colosseum.adapter.ListTopicAdapter
import com.sae.colosseum.databinding.FragmentBookmarkBinding
import com.sae.colosseum.interfaces.RecyclerViewListener
import com.sae.colosseum.model.entity.ResponseEntity
import com.sae.colosseum.model.entity.TopicInfoEntity
import com.sae.colosseum.network.ServerClient
import com.sae.colosseum.utils.GlobalApplication
import com.sae.colosseum.utils.ResultInterface
import com.sae.colosseum.view.DetailTopicActivity

class BookmarkFragment : Fragment() {
    var serverClient: ServerClient? = null
    var mContext: Context? = null
    var token: String? = null
    lateinit var recyclerListener: RecyclerViewListener<TopicInfoEntity, View>
    lateinit var binding: FragmentBookmarkBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_bookmark,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onResume() {
        super.onResume()
        setData()
    }

    fun init() {
        token = GlobalApplication.prefs.myEditText
        serverClient = ServerClient()
        setData()

        recyclerListener = object : RecyclerViewListener<TopicInfoEntity, View> {
            override fun onClickItem(
                item: TopicInfoEntity,
                clickedView: View,
                itemView: View
            ) {
                val intent = Intent(mContext, DetailTopicActivity::class.java)
                val topicId: Int = item.id

                intent.putExtra("topicId", topicId)
                mContext?.startActivity(intent)
            }
        }
    }

    private fun setData() {
        serverClient?.getTopicLike(token, object : ResultInterface<ResponseEntity> {
            override fun result(value: ResponseEntity) {
                val adapter = ListTopicAdapter(value.data.topics, recyclerListener)
                binding.listBookmark.adapter = adapter
                binding.listBookmark.layoutManager = LinearLayoutManager(mContext)
            }
        })
    }
}
