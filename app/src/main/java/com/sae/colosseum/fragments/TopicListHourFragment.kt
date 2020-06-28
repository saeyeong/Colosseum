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
import com.sae.colosseum.R
import com.sae.colosseum.adapter.DetailTopicAdapter
import com.sae.colosseum.adapter.TopicListAdapter
import com.sae.colosseum.databinding.FragmentTopicListHourBinding
import com.sae.colosseum.interfaces.RecyclerViewListener
import com.sae.colosseum.model.entity.ResponseEntity
import com.sae.colosseum.model.entity.TopicInfoEntity
import com.sae.colosseum.network.ServerClient
import com.sae.colosseum.utils.GlobalApplication
import com.sae.colosseum.interfaces.ResultInterface
import com.sae.colosseum.model.entity.ReplyEntity
import com.sae.colosseum.view.DetailTopicActivity

class TopicListHourFragment : Fragment() {
    var serverClient: ServerClient? = null
    var token: String? = null
    lateinit var recyclerListener: RecyclerViewListener<View, Int, View>
    lateinit var binding: FragmentTopicListHourBinding
    lateinit var adapter: TopicListAdapter
    var topics: ArrayList<TopicInfoEntity>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_topic_list_hour,container,false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    fun init() {
        token = GlobalApplication.prefs.userToken
        serverClient = ServerClient()
        setData()

        recyclerListener = object : RecyclerViewListener<View, Int, View> {
            override fun onClickItem(
                clickedView: View,
                position: Int,
                itemView: View
            ) {
                val intent = Intent(context, DetailTopicActivity::class.java)
                val topicId: Int = topics?.get(position)?.id ?: 0

                intent.putExtra("topicId", topicId)
                context?.startActivity(intent)
            }

        }

        binding.list.layoutManager = LinearLayoutManager(context)
        adapter = TopicListAdapter(topics, recyclerListener)
        binding.list.adapter = adapter
    }

    private fun setData() {
        serverClient?.getTopicList(token, object :
            ResultInterface<ResponseEntity, Boolean> {
            override fun result(value: ResponseEntity?, boolean: Boolean) {
                if(boolean) {
                    value?.let {
                        topics = it.data.topics
                        adapter.list = it.data.topics
                        adapter.notifyDataSetChanged()
                    }
                }
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