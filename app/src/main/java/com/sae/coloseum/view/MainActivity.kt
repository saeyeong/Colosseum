package com.sae.coloseum.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.sae.coloseum.R
import com.sae.coloseum.databinding.ActivityMainBinding
import com.sae.coloseum.fregments.AlarmFragment
import com.sae.coloseum.fregments.HeartFragment
import com.sae.coloseum.fregments.HomeFragment
import com.sae.coloseum.fregments.SettingFragment

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
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

    override fun onClick(v: View?) {
        when (v) {
            binding.home-> {
                replaceFragment(HomeFragment())
            }
            binding.heart -> {
                replaceFragment(HeartFragment())
            }
            binding.alarm -> {
                replaceFragment(AlarmFragment())
            }
            binding.setting -> {
                replaceFragment(SettingFragment())
            }
        }
        binding.home.getBackground()?.setAlpha(100)
        binding.heart.getBackground()?.setAlpha(100)
        binding.alarm.getBackground()?.setAlpha(100)
        binding.setting.getBackground()?.setAlpha(100)
        v?.getBackground()?.setAlpha(255)
    }

    override fun onBackPressed() {
        fm?.let {
            if (it.fragments.size > 1) {
                super.onBackPressed()
            } else {
                finish()
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

        binding.heart.getBackground()?.setAlpha(100)
        binding.alarm.getBackground()?.setAlpha(100)
        binding.setting.getBackground()?.setAlpha(100)
    }

    fun setListener(){
        binding.home.setOnClickListener(this)
        binding.heart.setOnClickListener(this)
        binding.alarm.setOnClickListener(this)
        binding.setting.setOnClickListener(this)
    }

    fun firstInitFragment() {
        replaceFragment(HomeFragment())
    }


    fun replaceFragment(fragment: Fragment?) {
        fragment?.let {
            if (isCurrentFragmentEqual(it)) {

            } else {
                currentFragmentTag = fragment::class.java.simpleName
                ft= fm?.beginTransaction()
                ft?.replace(R.id.container, it)
                ft?.addToBackStack(null)
                ft?.commit()
            }
        }
    }

    fun isCurrentFragmentEqual(fragment: Fragment): Boolean {
        currentFragmentTag?.let {
            return currentFragmentTag == fragment::class.java.simpleName
        } ?: run {
            return false
        }
    }
}
