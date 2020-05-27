package com.sae.coloseum.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.sae.coloseum.fregments.PostList1Fragment
import com.sae.coloseum.fregments.PostList2Fragment
import com.sae.coloseum.fregments.PostList3Fragment

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
        return PostList1Fragment()
    }

    fun addToListFragmentItem(fragment: Fragment) {
        fragmentList?.let {
            it.add(fragment)
        }
    }

    fun makeTempPage() {
        fragmentList?.let {
            // addToListFragmentItem(PostList1Fragment()) 이렇게 말고 아래와 같은 패턴으로 프래그먼트 생성할 것
            addToListFragmentItem(PostList1Fragment.newInstance()) //
            addToListFragmentItem(PostList2Fragment())
            addToListFragmentItem(PostList3Fragment())
        }

    }
}
