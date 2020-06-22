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
import com.sae.colosseum.databinding.FragmentPasswordBinding
import com.sae.colosseum.network.ServerClient
import com.sae.colosseum.utils.GlobalApplication
import com.sae.colosseum.utils.ResultInterface

class PasswordFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentPasswordBinding
    var mContext: Context? = null
    var imm: InputMethodManager? = null
    var serverClient: ServerClient? = null
    var token: String? = null
    var editPassword: String? = null
    var editNewPassword: String? = null
    var editCheckNewPassword: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_password,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onDestroyView() {
        super.onDestroyView()
        hideKeyboard()
    }

    fun init() {
        setListener()

        imm = mContext?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager?

        serverClient = ServerClient()
        token = GlobalApplication.prefs.myEditText
    }

    private fun setListener() {
        binding.btnOk.setOnClickListener(this)
        binding.btnBack.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        editPassword = binding.editPassword.text.toString()
        editNewPassword = binding.editNewPassword.text.toString()
        editCheckNewPassword = binding.editNewPassword.text.toString()

        when(v) {
            binding.btnOk -> {
                if(editPassword.isNullOrEmpty() || editNewPassword.isNullOrEmpty() || editCheckNewPassword.isNullOrEmpty()) {

                } else if(editPassword?.length !in 8..15) {
                    Toast.makeText(mContext, "비밀번호는 8자리 이상 15자리 이하로 입력해주세요.", Toast.LENGTH_LONG).show()
                } else if(editNewPassword != editCheckNewPassword) {
                    Toast.makeText(mContext, "새 비밀번호 확인과 같지 않습니다.", Toast.LENGTH_LONG).show()
                } else if(editPassword == editNewPassword) {
                    Toast.makeText(mContext, "현재 비밀번호와 새 비밀번호가 같습니다.", Toast.LENGTH_LONG).show()
                } else {
                    hideKeyboard()
                    patchUser()
                }
            }
            binding.btnBack -> {
                fragmentManager?.run {
                    beginTransaction().remove(this@PasswordFragment).commit()
                    popBackStack()
                }
            }
        }
    }

    companion object{
        fun newInstance(): Fragment{
            val args = Bundle()

            val fragment = PasswordFragment()
            fragment.arguments = args
            return fragment
        }
    }

    fun patchUser() {
        serverClient?.patchUser(token, editPassword, editCheckNewPassword,
            object : ResultInterface<Boolean> {
                override fun result(value: Boolean) {
                    if(value) {
                        Toast.makeText(mContext, "비밀번호를 변경했습니다.", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(mContext, "비밀번호 변경에 실패했습니다.", Toast.LENGTH_LONG).show()
                    }
                }
            })
    }

    fun hideKeyboard(){
        imm?.hideSoftInputFromWindow(binding.editPassword.windowToken, 0)
    }
}