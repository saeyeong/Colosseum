package com.sae.colosseum.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sae.colosseum.R
import com.sae.colosseum.databinding.FragmentSettingBinding

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
