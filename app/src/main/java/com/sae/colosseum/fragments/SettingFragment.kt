package com.sae.colosseum.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.sae.colosseum.R
import com.sae.colosseum.databinding.FragmentSettingBinding
import com.sae.colosseum.utils.GlobalApplication
import com.sae.colosseum.view.LoginActivity

class SettingFragment : Fragment(), View.OnClickListener {
    lateinit var binding: FragmentSettingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_setting,container,false)

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    fun init() {
        setListener()
    }

    fun setListener() {
        binding.btnLogout.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        GlobalApplication.prefs.myEditText = ""
        var intentLogin: Intent = Intent(activity, LoginActivity::class.java)
        startActivity(intentLogin)
        activity?.finish();
    }
}
