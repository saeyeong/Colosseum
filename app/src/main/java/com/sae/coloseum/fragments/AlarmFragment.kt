package com.sae.coloseum.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.sae.coloseum.R
import com.sae.coloseum.adapter.AlarmListAdapter
import com.sae.coloseum.databinding.FragmentAlarmBinding
import com.sae.coloseum.model.DataModel
import kotlinx.android.synthetic.main.fragment_alarm.*

class AlarmFragment : Fragment() {
    var model: DataModel? = null
    var adapter: AlarmListAdapter? = null

    lateinit var binding: FragmentAlarmBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_alarm,container,false)

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    fun init() {
        model = DataModel()
        adapter = AlarmListAdapter(model?.itemsList3)

        alarm_list.adapter = adapter
        alarm_list.layoutManager = LinearLayoutManager(context)
    }
}
