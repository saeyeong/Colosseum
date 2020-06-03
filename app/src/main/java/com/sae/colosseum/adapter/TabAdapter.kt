package com.sae.colosseum.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.sae.colosseum.fragments.*

class TabAdapter(fm: FragmentManager) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    var fragmentList: ArrayList<Fragment>? = null

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
        return PostListHourFragment()
    }

    fun addToListFragmentItem(fragment: Fragment) {
        fragmentList?.let {
            it.add(fragment)
        }
    }

    fun makeTempPage() {
        fragmentList?.let {
            addToListFragmentItem(PostListHourFragment.newInstance())
            addToListFragmentItem(PostListWeekFragment.newInstance())
            addToListFragmentItem(PostListMonthFragment.newInstance())
        }
    }
}
