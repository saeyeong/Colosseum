package com.sae.colosseum.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.sae.colosseum.R
import com.sae.colosseum.databinding.FragmentPostListMonthBinding
import com.sae.colosseum.model.DataModel

class TopicListMonthFragment : Fragment() {
    var model: DataModel? = null

    lateinit var binding: FragmentPostListMonthBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_post_list_month,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    fun init() {
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
