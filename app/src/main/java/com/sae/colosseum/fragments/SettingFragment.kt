package com.sae.colosseum.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.sae.colosseum.R
import com.sae.colosseum.databinding.FragmentSettingBinding
import com.sae.colosseum.model.entity.ResponseEntity
import com.sae.colosseum.model.entity.UserEntity
import com.sae.colosseum.network.ServerClient
import com.sae.colosseum.utils.GlobalApplication
import com.sae.colosseum.utils.ResultInterface
import com.sae.colosseum.view.LoginActivity

class SettingFragment : Fragment(), View.OnClickListener {
    lateinit var binding: FragmentSettingBinding
    var serverClient: ServerClient? = null
    var token: String? = null

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
        serverClient = ServerClient()
        token = GlobalApplication.prefs.myEditText
    }

    fun setListener() {
        binding.btnLogout.setOnClickListener(this)
        binding.btnMemberOut.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v) {
            binding.btnLogout -> {
                GlobalApplication.prefs.myEditText = ""
                GlobalApplication.loginUser = UserEntity("","","","","","")
                val intentLogin = Intent(activity, LoginActivity::class.java)
                startActivity(intentLogin)
                activity?.finish()
            }
            binding.btnMemberOut -> {
                serverClient?.deleteUser(token, "동의", object: ResultInterface<ResponseEntity> {
                    override fun result(value: ResponseEntity) {
                        val intentLogin = Intent(activity, LoginActivity::class.java)
                        startActivity(intentLogin)
                        activity?.finish()
                    }
                })
                GlobalApplication.prefs.myEditText = ""
                GlobalApplication.loginUser = UserEntity("","","","","","")
            }
        }
    }
}
