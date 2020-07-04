package com.sae.colosseum.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.sae.colosseum.R
import com.sae.colosseum.adapter.NotificationListAdapter
import com.sae.colosseum.databinding.FragmentNotificationBinding
import com.sae.colosseum.model.DataModel
import com.sae.colosseum.model.entity.ResponseEntity
import com.sae.colosseum.network.ServerClient
import com.sae.colosseum.utils.GlobalApplication
import com.sae.colosseum.interfaces.ResultInterface
import com.sae.colosseum.view.MainActivity
import kotlinx.android.synthetic.main.activity_main.*

class NotificationFragment : Fragment() {
    var model: DataModel? = null
    var adapter: NotificationListAdapter? = null
    lateinit var serverClient: ServerClient

    val token = GlobalApplication.prefs.userToken
    lateinit var binding: FragmentNotificationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_notification,container,false)

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    fun init() {
        serverClient = ServerClient()
        getNotification()
    }

    fun postNotification(notiId: Int){
        serverClient.postNotification(token, notiId, object :
            ResultInterface<ResponseEntity, Boolean> {
            override fun result(value: ResponseEntity?, boolean: Boolean) {
                (activity as MainActivity).num_notification.visibility = View.GONE
            }
        })
    }

    private fun getNotification() {
        serverClient.getNotification(token, true, object :
            ResultInterface<ResponseEntity, Boolean> {
            override fun result(value: ResponseEntity?, boolean: Boolean) {
                if(boolean) {
                    value?.let {
                        val notifications = it.data.notifications
                        adapter = NotificationListAdapter(notifications)
                        binding.list.adapter = adapter
                        binding.list.layoutManager = LinearLayoutManager(context)

                        postNotification(notifications[0].id)
                    }
                }
            }
        })
    }

    companion object{
        fun newInstance(): Fragment{
            val args = Bundle()

            val fragment = NotificationFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
