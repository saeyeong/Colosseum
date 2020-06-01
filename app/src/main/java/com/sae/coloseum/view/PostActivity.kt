package com.sae.coloseum.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.sae.coloseum.R
import com.sae.coloseum.adapter.CmtListAdapter
import com.sae.coloseum.databinding.ActivityPostBinding
import com.sae.coloseum.model.DataModel
import kotlinx.android.synthetic.main.activity_post.*

class PostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPostBinding

    var model: DataModel? = null
    var adapter: CmtListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_post)

        init()
    }

    fun init() {
        model = DataModel()
        adapter = CmtListAdapter(model?.itemsList2)

        cmt_list.adapter = adapter
        cmt_list.layoutManager = LinearLayoutManager(this)
    }
}
