package com.sae.colosseum.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.sae.colosseum.R
import com.sae.colosseum.databinding.ActivityMainBinding
import com.sae.colosseum.fragments.NotificationFragment
import com.sae.colosseum.fragments.BookmarkFragment
import com.sae.colosseum.fragments.HomeFragment
import com.sae.colosseum.fragments.SettingFragment
import com.sae.colosseum.interfaces.ResultInterface
import com.sae.colosseum.model.entity.ResponseEntity
import com.sae.colosseum.utils.BaseActivity

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
        getNotification()
        super.onResume()
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.tabHome-> {
                replaceFragment(HomeFragment.newInstance())
            }
            binding.tabBookmark -> {
                replaceFragment(BookmarkFragment.newInstance())
            }
            binding.tabNotification -> {
                replaceFragment(NotificationFragment.newInstance())
            }
            binding.tabSetting -> {
                replaceFragment(SettingFragment.newInstance())
            }
        }

        binding.tabHome.background.alpha = 100
        binding.tabBookmark.background.alpha = 100
        binding.tabNotification.background.alpha = 100
        binding.tabSetting.background.alpha = 100
        v?.background?.alpha = 255
    }

    override fun onBackPressed() {
        val curTime: Long = System.currentTimeMillis()
        val gapTime: Long = curTime - backBtnTime

        fm?.let {
            if (it.fragments.size > 1) {
                super.onBackPressed()
            } else {
                if(gapTime in 0..1000) {
                    finish()
                }
                else {
                    backBtnTime = curTime
                }
            }
            return
        }
        super.onBackPressed()
    }

    fun init(){
        setListener()
        getNotification()
        supportFragmentManager.let {
            fm = it
            ft = it.beginTransaction()
        }

        binding.tabBookmark.background.alpha = 100
        binding.tabNotification.background.alpha = 100
        binding.tabSetting.background.alpha = 100
    }

    private fun setListener(){
        binding.tabHome.setOnClickListener(this)
        binding.tabBookmark.setOnClickListener(this)
        binding.tabNotification.setOnClickListener(this)
        binding.tabSetting.setOnClickListener(this)
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

    fun getNotification() {
        serverClient.getNotification(token, false, object :
            ResultInterface<ResponseEntity, Boolean> {
            override fun result(value: ResponseEntity?, boolean: Boolean) {
                if(boolean) {
                    val unreadNotyCount = value?.data?.unread_noty_count ?: 0
                    if(unreadNotyCount != 0) {
                        binding.numNotification.text = unreadNotyCount.toString()
                        binding.numNotification.visibility = View.VISIBLE
                    }
                }
            }
        })
    }
}
