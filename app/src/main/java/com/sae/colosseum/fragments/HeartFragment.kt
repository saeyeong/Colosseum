package com.sae.colosseum.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.sae.colosseum.R
import com.sae.colosseum.adapter.TopicListAdapter
import com.sae.colosseum.databinding.FragmentHeartBinding
import com.sae.colosseum.model.DataModel

class HeartFragment : Fragment() {
    var model: DataModel? = null
    var adapter: TopicListAdapter? = null

    lateinit var binding: FragmentHeartBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_heart,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    fun init() {
//        model = DataModel()
//        adapter = PostListAdapter(model?.itemsList)
//
//        post_list.adapter = adapter
//        post_list.layoutManager = LinearLayoutManager(context)
    }
}
