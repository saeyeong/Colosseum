package com.sae.colosseum.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.sae.colosseum.R
import com.sae.colosseum.databinding.ActivityMainBinding
import com.sae.colosseum.fragments.AlarmFragment
import com.sae.colosseum.fragments.BookmarkFragment
import com.sae.colosseum.fragments.HomeFragment
import com.sae.colosseum.fragments.SettingFragment
import com.sae.colosseum.utils.BaseActivity
import com.sae.colosseum.utils.replaceFragment

class MainActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private var backBtnTime: Long = 0

    var fm: FragmentManager? = null
    var ft: FragmentTransaction? = null
    var currentFragmentTag: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        init()
    }

    override fun onStart() {
        super.onStart()

        firstInitFragment()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.home-> {
                replaceFragment(HomeFragment.newInstance())
            }
            binding.bookmark -> {
                replaceFragment(BookmarkFragment.newInstance())
            }
            binding.alarm -> {
                replaceFragment(AlarmFragment.newInstance())
            }
            binding.setting -> {
                replaceFragment(SettingFragment.newInstance())
            }
        }

        binding.home.background.alpha = 100
        binding.bookmark.background.alpha = 100
        binding.alarm.background.alpha = 100
        binding.setting.background.alpha = 100
        v?.background?.alpha = 255
    }

    override fun onBackPressed() {
        val curTime: Long = System.currentTimeMillis()
        val gapTime: Long = curTime - backBtnTime

        fm?.let {
            if (it.fragments.size > 1) {
                super.onBackPressed()
                Log.d("test","fragments.size > 1")
            } else {
                if(gapTime in 0..1000) {
                    Log.d("test","gapTime in 0..1000")
                    finish()
                }
                else {
                    Log.d("test","한번더")
                    backBtnTime = curTime
                }
            }
            return
        }
        super.onBackPressed()
    }

    fun init(){
        setListener()

        supportFragmentManager.let {
            fm = it
            ft = it.beginTransaction()
        }

        binding.bookmark.background.alpha = 100
        binding.alarm.background.alpha = 100
        binding.setting.background.alpha = 100
    }

    private fun setListener(){
        binding.home.setOnClickListener(this)
        binding.bookmark.setOnClickListener(this)
        binding.alarm.setOnClickListener(this)
        binding.setting.setOnClickListener(this)
    }

    private fun firstInitFragment() {
        replaceFragment(HomeFragment.newInstance())
    }

    private fun replaceFragment(fragment: Fragment?) {
        fragment?.let {
            if (isCurrentFragmentEqual(it)) {
                return@let
            }
            currentFragmentTag = fragment::class.java.simpleName
            ft = fm?.beginTransaction()
            ft?.replace(R.id.container, it)
            ft?.addToBackStack(null)
            ft?.commit()
        }
    }

    private fun isCurrentFragmentEqual(fragment: Fragment): Boolean {
        currentFragmentTag?.let {
            return currentFragmentTag == fragment::class.java.simpleName
        } ?: run {
            return false
        }
    }
}
