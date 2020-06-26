package com.sae.colosseum.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPreferences(context: Context) {
    private val PREFS_FILENAME = "colosseum"
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0)
    private val keyToken = "token"
    private val keyUserNickName = "nickName"
    private val keyUserEmail = "email"
    private val keyUserId = "id"

    var userToken: String?
        get() = prefs.getString(keyToken, "")
        set(value) = prefs.edit().putString(keyToken, value).apply()

    var userId: String?
        get() = prefs.getString(keyUserId, "")
        set(value) = prefs.edit().putString(keyUserId, value).apply()

    var userEmail: String?
        get() = prefs.getString(keyUserEmail, "")
        set(value) = prefs.edit().putString(keyUserEmail, value).apply()

    var userNickName: String?
        get() = prefs.getString(keyUserNickName, "")
        set(value) = prefs.edit().putString(keyUserNickName, value).apply()

}