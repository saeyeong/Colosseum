package com.sae.colosseum.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.sae.colosseum.R
import com.sae.colosseum.databinding.FragmentPostListHourBinding
import com.sae.colosseum.network.ServerClient
import com.sae.colosseum.utils.GlobalApplication
import kotlinx.android.synthetic.main.fragment_post_list_hour.*

class PostListHourFragment : Fragment() {
    var serverClient: ServerClient? = null

    lateinit var binding: FragmentPostListHourBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_post_list_hour,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    fun init() {
        serverClient = ServerClient()
        val token = GlobalApplication.prefs.myEditText
        var context: Context? = context
        var postList = post_list
        if (context != null) {
            serverClient?.getMainPostList(token, context, postList)
        }

    }

    companion object{

              fun newInstance(): Fragment{
                val args = Bundle()

                val fragment = PostListHourFragment()
                fragment.arguments = args
                return fragment
            }
        }
    }
