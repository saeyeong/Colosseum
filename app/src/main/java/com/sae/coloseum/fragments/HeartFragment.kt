package com.sae.coloseum.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.sae.coloseum.R
import com.sae.coloseum.adapter.PostListAdapter
import com.sae.coloseum.databinding.FragmentHeartBinding
import com.sae.coloseum.databinding.FragmentPostList1Binding
import com.sae.coloseum.model.DataModel
import kotlinx.android.synthetic.main.fragment_post_list1.*

class HeartFragment : Fragment() {
    var model: DataModel? = null
    var adapter: PostListAdapter? = null

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
        model = DataModel()
        adapter = PostListAdapter(model?.itemsList)

        post_list.adapter = adapter
        post_list.layoutManager = LinearLayoutManager(context)
    }
}
