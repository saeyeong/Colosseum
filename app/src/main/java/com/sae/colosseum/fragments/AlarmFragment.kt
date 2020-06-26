package com.sae.colosseum.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.sae.colosseum.R
import com.sae.colosseum.adapter.AlarmListAdapter
import com.sae.colosseum.databinding.FragmentAlarmBinding
import com.sae.colosseum.model.DataModel
import com.sae.colosseum.model.entity.ResponseEntity
import com.sae.colosseum.network.ServerClient
import com.sae.colosseum.utils.GlobalApplication
import com.sae.colosseum.interfaces.ResultInterface

class AlarmFragment : Fragment() {
    var model: DataModel? = null
    var adapter: AlarmListAdapter? = null
    lateinit var serverClient: ServerClient

    val token = GlobalApplication.prefs.userToken
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
        serverClient = ServerClient()
        setData()
    }

    private fun setData() {
        serverClient.getNotification(token, object :
            ResultInterface<ResponseEntity, Boolean> {
            override fun result(value: ResponseEntity?, boolean: Boolean) {
                if(boolean) {
                    value?.let {
                        adapter = AlarmListAdapter(it.data.notifications)
                        binding.list.adapter = adapter
                        binding.list.layoutManager = LinearLayoutManager(context)
                    }
                }
            }
        })
    }

    companion object{
        fun newInstance(): Fragment{
            val args = Bundle()

            val fragment = AlarmFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
