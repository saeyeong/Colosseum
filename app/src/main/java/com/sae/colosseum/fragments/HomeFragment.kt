package com.sae.colosseum.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import com.sae.colosseum.R
import com.sae.colosseum.adapter.TopicListPagerAdapter
import com.sae.colosseum.databinding.FragmentHomeBinding
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(), ViewPager.OnPageChangeListener {

    lateinit var binding: FragmentHomeBinding
    private val topicListPagerAdapter : TopicListPagerAdapter by lazy {
        TopicListPagerAdapter(childFragmentManager)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun onResume() {
        super.onResume()
        init()
    }

    fun init() {
        binding.pager.adapter = topicListPagerAdapter
        binding.pager.addOnPageChangeListener(this)
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        when (position) {
            0-> {
                collapsing.title = getString(R.string.post_list_hour)
            }
            1 -> {
                collapsing.title = getString(R.string.post_list_week)
            }
            2 -> {
                collapsing.title = getString(R.string.post_list_month)
            }
        }
    }

    companion object{
        fun newInstance(): Fragment{
            val args = Bundle()

            val fragment = HomeFragment()
            fragment.arguments = args
            return fragment
        }
    }
}