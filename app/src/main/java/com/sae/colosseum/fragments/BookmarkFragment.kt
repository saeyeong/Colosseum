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
import com.sae.colosseum.R
import com.sae.colosseum.adapter.TopicListAdapter
import com.sae.colosseum.databinding.FragmentBookmarkBinding
import com.sae.colosseum.interfaces.RecyclerViewListener
import com.sae.colosseum.model.entity.ResponseEntity
import com.sae.colosseum.model.entity.TopicInfoEntity
import com.sae.colosseum.network.ServerClient
import com.sae.colosseum.utils.GlobalApplication
import com.sae.colosseum.interfaces.ResultInterface
import com.sae.colosseum.view.DetailTopicActivity

class BookmarkFragment : Fragment() {
    var serverClient: ServerClient? = null
    var token: String? = null
    lateinit var recyclerListener: RecyclerViewListener<TopicInfoEntity, View>
    lateinit var binding: FragmentBookmarkBinding
    var adapter: TopicListAdapter? = null
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

    override fun onResume() {
        super.onResume()
        setData()
    }

    fun init() {
        token = GlobalApplication.prefs.userToken
        serverClient = ServerClient()
        setData()

        recyclerListener = object : RecyclerViewListener<TopicInfoEntity, View> {
            override fun onClickItem(
                item: TopicInfoEntity,
                clickedView: View,
                itemView: View
            ) {
                val intent = Intent(context, DetailTopicActivity::class.java)
                val topicId: Int = item.id

                intent.putExtra("topicId", topicId)
                context?.startActivity(intent)
            }
        }
    }

    private fun setData() {
        serverClient?.getTopicLike(token, object :
            ResultInterface<ResponseEntity, Boolean> {
            override fun result(value: ResponseEntity?, boolean: Boolean) {
                if(boolean) {
                    value?.let {
                        adapter = TopicListAdapter(it.data.topics, recyclerListener)
                        binding.list.adapter = adapter
                        binding.list.layoutManager = LinearLayoutManager(context)
                    }
                }
            }
        })
    }

    companion object{
        fun newInstance(): Fragment{
            val args = Bundle()

            val fragment = BookmarkFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
