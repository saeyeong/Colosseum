package com.sae.coloseum.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.sae.coloseum.R
import com.sae.coloseum.adapter.CmtListAdapter
import com.sae.coloseum.databinding.ActivityPostBinding
import com.sae.coloseum.model.DataModel
import kotlinx.android.synthetic.main.activity_post.*

class PostActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityPostBinding

    var model: DataModel? = null
    var adapter: CmtListAdapter? = null
    var tag: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_post)

        init()
    }

    override fun onClick(v: View?) {
        if(tag == "off") {
            binding.bookmark.setImageResource(R.drawable.bookmark_on)
            tag = "on"
        } else {
            binding.bookmark.setImageResource(R.drawable.bookmark_off)
            tag = "off"
        }
    }

    fun init() {
        setListener()
        model = DataModel()
        adapter = CmtListAdapter(model?.itemsList2)
        tag = "off"

        cmt_list.adapter = adapter
        cmt_list.layoutManager = LinearLayoutManager(this)
    }

    private fun setListener(){
        binding.bookmark.setOnClickListener(this)
    }
}
