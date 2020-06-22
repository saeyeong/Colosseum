package com.sae.colosseum.model

import com.sae.colosseum.network.ServerClient
import com.sae.colosseum.utils.GlobalApplication
import kotlin.collections.ArrayList

class DataModel {
    var token: String? = GlobalApplication.prefs.myEditText
    var serverClient: ServerClient = ServerClient()

}
