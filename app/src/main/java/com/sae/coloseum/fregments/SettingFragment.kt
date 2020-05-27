package com.sae.coloseum.fregments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.sae.coloseum.R
import com.sae.coloseum.databinding.FragmentSettingBinding

class SettingFragment : Fragment() {
    lateinit var layoutView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        layoutView = inflater.inflate(R.layout.fragment_setting, container, false)
        return layoutView
    }
}
