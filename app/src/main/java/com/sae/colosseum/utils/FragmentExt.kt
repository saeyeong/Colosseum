package com.sae.colosseum.utils

import androidx.fragment.app.Fragment
import com.sae.colosseum.network.ServerClient

fun Fragment.replaceFragment(layoutId: Int, fragment: Fragment) {
    fragmentManager?.beginTransaction()?.run {
        replace(layoutId, fragment)
        commitNow()
    }
}

fun Fragment.replaceFragmentStack(layoutId: Int, fragment: Fragment) {
    fragmentManager?.beginTransaction()?.run {
        replace(layoutId, fragment)
        addToBackStack(null)
        commit()
    }
}
