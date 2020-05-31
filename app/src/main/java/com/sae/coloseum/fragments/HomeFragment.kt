package com.sae.coloseum.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import com.sae.coloseum.R
import com.sae.coloseum.adapter.TabAdapter
import com.sae.coloseum.databinding.FragmentHomeBinding

class HomeFragment : Fragment(), ViewPager.OnPageChangeListener {

    lateinit var binding: FragmentHomeBinding
    private val tabAdapter : TabAdapter by lazy {
        TabAdapter(childFragmentManager)
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

    fun init() {
        binding.viewPager.adapter = tabAdapter

        binding.viewPager.addOnPageChangeListener(this)

    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        Log.d("test",position.toString())

        when (position) {
            0-> {
                binding.appToolbar.title = "0시 인기글"
            }

            1 -> {
                binding.appToolbar.title = "주간 인기글"
            }
            2 -> {
                binding.appToolbar.title = "월간 인기글"

            }
        }
    }
}