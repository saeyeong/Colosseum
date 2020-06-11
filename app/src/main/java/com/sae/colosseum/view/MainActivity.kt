package com.sae.colosseum.view

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.sae.colosseum.R
import com.sae.colosseum.databinding.ActivityMainBinding
import com.sae.colosseum.utils.BaseActivity

class MainActivity : BaseActivity(), View.OnClickListener {

    private var currentLayoutIndex = -1

    private lateinit var binding: ActivityMainBinding
    var fm: FragmentManager? = null
    var ft: FragmentTransaction? = null

    private val fragLayouts = ArrayList<LinearLayout>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        init()
    }

    override fun onStart() {
        super.onStart()

        if(currentLayoutIndex == -1) {
            firstInitFragment()
        }
    }

    override fun onClick(v: View?) {

        when (v) {
            binding.home-> {
                replaceFragment(0)
            }
            binding.heart -> {
                replaceFragment(1)
            }
            binding.alarm -> {
                replaceFragment(2)
            }
            binding.setting -> {
                replaceFragment(3)
            }
        }
        binding.home.background.alpha = 100
        binding.heart.background.alpha = 100
        binding.alarm.background.alpha = 100
        binding.setting.background.alpha = 100
        v?.background?.alpha = 255
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

        fragLayouts.add(binding.homeFragLayout)
        fragLayouts.add(binding.heartFragLayout)
        fragLayouts.add(binding.alarmFragLayout)
        fragLayouts.add(binding.settingFragLayout)

        supportFragmentManager.let {
            fm = it
            ft = it.beginTransaction()
        }

        binding.heart.background.alpha = 100
        binding.alarm.background.alpha = 100
        binding.setting.background.alpha = 100
    }

    fun setListener(){
        binding.home.setOnClickListener(this)
        binding.heart.setOnClickListener(this)
        binding.alarm.setOnClickListener(this)
        binding.setting.setOnClickListener(this)
    }


    fun firstInitFragment() {
        replaceFragment(0)
    }


    fun replaceFragment(layoutIndex: Int) {


        for (i in fragLayouts.indices) {

            if (i == layoutIndex) {
                fragLayouts[i].visibility = View.VISIBLE
            }
            else {
                fragLayouts[i].visibility = View.GONE
            }

        }
        currentLayoutIndex = layoutIndex
    }
}
