package com.sae.colosseum.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.sae.colosseum.fragments.*

class TopicListPagerAdapter(fm: FragmentManager) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private var fragmentList: ArrayList<Fragment>? = null

    init {
        fragmentList = ArrayList()
        makeTempPage()
    }

    override fun getCount(): Int = fragmentList?.let {
        return@let it.size
    } ?: kotlin.run {
        return 0
    }

    override fun getItem(position: Int): Fragment {
        fragmentList?.let {

            return it[position]
        }
        return TopicListFragment()
    }

    private fun addToListFragmentItem(fragment: Fragment) {
        fragmentList?.let {
            it.add(fragment)
        }
    }

    private fun makeTempPage() {
        fragmentList?.let {
            addToListFragmentItem(TopicListFragment.newInstance())
            addToListFragmentItem(TopicListFragment.newInstance())
            addToListFragmentItem(TopicListFragment.newInstance())
        }
    }
}
