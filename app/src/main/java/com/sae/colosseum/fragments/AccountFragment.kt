package com.sae.colosseum.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.sae.colosseum.R
import com.sae.colosseum.databinding.FragmentAccountBinding
import com.sae.colosseum.utils.replaceFragmentStack

class AccountFragment : Fragment(), View.OnClickListener {
    lateinit var binding: FragmentAccountBinding
    private var ft: FragmentTransaction? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
        R.layout.fragment_account,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    fun init() {
        setListener()
        ft = fragmentManager?.beginTransaction()
    }

    private fun setListener() {
        binding.btnBack.setOnClickListener(this)
        binding.btnUserInfo.setOnClickListener(this)
        binding.btnPassword.setOnClickListener(this)
        binding.btnMyReply.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v) {
            binding.btnUserInfo -> {
                replaceFragmentStack(R.id.container, UserInfoFragment.newInstance())
            }
            binding.btnPassword -> {
                replaceFragmentStack(R.id.container, PasswordFragment.newInstance())
            }
            binding.btnMyReply -> {
                replaceFragmentStack(R.id.container, UserReplyFragment.newInstance())
            }
            binding.btnBack -> {
                fragmentManager?.run {
                    beginTransaction().remove(this@AccountFragment).commit()
                    popBackStack()
                }
            }
        }
    }

    companion object{
        fun newInstance(): Fragment{
            val args = Bundle()

            val fragment = AccountFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
