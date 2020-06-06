package com.sae.colosseum.model

import com.sae.colosseum.network.ServerClient
import com.sae.colosseum.utils.GlobalApplication
import kotlin.collections.ArrayList

class DataModel {
    var token: String? = GlobalApplication.prefs.myEditText
    var serverClient: ServerClient = ServerClient()
//    var dataModel: DataModel = DataModel()
//
//    var test = ServerClient()
//
//    fun dataTest(topicId: String?, callback: ResultInterface<TopicEntity>) {
//        test.getTopic(token, topicId, object : ResultInterface<TopicEntity> {
//            override fun result(value: TopicEntity) {
//                var topicInfo = value.data.topic
//            }
//        })
//    }


}
