package com.sae.colosseum.fragments

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.sae.colosseum.R
import com.sae.colosseum.databinding.FragmentUserInfoBinding
import com.sae.colosseum.network.ServerClient
import com.sae.colosseum.utils.GlobalApplication
import com.sae.colosseum.interfaces.ResultInterface
import com.sae.colosseum.model.entity.ResponseEntity

class UserInfoFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentUserInfoBinding
    var imm: InputMethodManager? = null
    var serverClient: ServerClient? = null
    var token: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_user_info,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        hideKeyboard()
    }

    fun init() {
        setListener()
        token = GlobalApplication.prefs.userToken
        binding.userEmail.text = GlobalApplication.prefs.userEmail
        serverClient = ServerClient()
        imm = context?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager?
        setData()
    }

    private fun setListener() {
        binding.userNickName.setOnClickListener(this)
        binding.btnOk.setOnClickListener(this)
        binding.btnBack.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v) {
            binding.userNickName -> {
                v.visibility = View.GONE
                binding.editUserNickName.visibility = View.VISIBLE
                binding.btnOk.visibility = View.VISIBLE
                binding.editUserNickName.requestFocus()
                imm?.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
                binding.editUserNickName.setSelection(binding.editUserNickName.length())
            }
            binding.btnOk -> {
                serverClient?.patchUser(token, binding.editUserNickName.text.toString(),
                    object :
                        ResultInterface<ResponseEntity, Boolean> {
                        override fun result(value: ResponseEntity?, boolean: Boolean) {
                            if(boolean) {
                                setData()
                                binding.editUserNickName.visibility = View.GONE
                                binding.userNickName.visibility = View.VISIBLE
                                binding.btnOk.visibility = View.GONE
                                hideKeyboard()
                            } else {
                                Toast.makeText(context, "닉네임 변경에 실패했습니다.", Toast.LENGTH_LONG).show()
                            }
                        }
                    })
            }
            binding.btnBack -> {
                fragmentManager?.run {
                    beginTransaction().remove(this@UserInfoFragment).commit()
                    popBackStack()
                }
            }
        }
    }

    private fun setData() {
        serverClient?.getUserInfo(token, object :
            ResultInterface<ResponseEntity, Boolean> {
            override fun result(value: ResponseEntity?, boolean: Boolean) {
                if(boolean) {
                    binding.userNickName.text = GlobalApplication.prefs.userNickName
                    binding.editUserNickName.setText(GlobalApplication.prefs.userNickName)
                } else {
                    Toast.makeText(context, "정보 불러오기에 실패했습니다.", Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    fun hideKeyboard(){
        imm?.hideSoftInputFromWindow(binding.editUserNickName.windowToken, 0)
    }

    companion object{
        fun newInstance(): Fragment{
            val args = Bundle()

            val fragment = UserInfoFragment()
            fragment.arguments = args
            return fragment
        }
    }
}