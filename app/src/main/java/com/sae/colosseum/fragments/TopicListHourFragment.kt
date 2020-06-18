package com.sae.colosseum.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
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
import com.sae.colosseum.databinding.FragmentTopicListHourBinding
import com.sae.colosseum.interfaces.RecyclerViewListener
import com.sae.colosseum.model.entity.ResponseEntity
import com.sae.colosseum.model.entity.TopicInfoEntity
import com.sae.colosseum.network.ServerClient
import com.sae.colosseum.utils.GlobalApplication
import com.sae.colosseum.utils.ResultInterface
import com.sae.colosseum.view.DetailTopicActivity

class TopicListHourFragment : Fragment() {
    var serverClient: ServerClient? = null
    var mContext: Context? = null
    var token: String? = null
    var glide: RequestManager? = null
    lateinit var recyclerListener: RecyclerViewListener<TopicInfoEntity, View>
    lateinit var binding: FragmentTopicListHourBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_topic_list_hour,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
        mContext?.let {
            glide = Glide.with(it)
        }
    }

    fun init() {
        token = GlobalApplication.prefs.myEditText
        serverClient = ServerClient()
        setData()

        recyclerListener = object : RecyclerViewListener<TopicInfoEntity, View> {
            override fun onClickItem(
                item: TopicInfoEntity,
                clickedView: View,
                itemReplyView: View
            ) {
                val intent = Intent(mContext, DetailTopicActivity::class.java)
                val topicId: Int = item.id

                intent.putExtra("topicId", topicId)
                mContext?.startActivity(intent)
            }

        }
    }

    private fun setData() {
        serverClient?.getTopicList(token, object : ResultInterface<ResponseEntity> {
            override fun result(value: ResponseEntity) {
                val adapter = ListTopicAdapter(glide, value.data.topics, recyclerListener)
                binding.topicList.adapter = adapter
                binding.topicList.layoutManager = LinearLayoutManager(mContext)
            }
        })
    }

    companion object{
          fun newInstance(): Fragment{
            val args = Bundle()

            val fragment = TopicListHourFragment()
            fragment.arguments = args
            return fragment
        }
    }


}