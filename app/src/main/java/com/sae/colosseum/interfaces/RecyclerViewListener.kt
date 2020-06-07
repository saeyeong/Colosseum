package com.sae.colosseum.interfaces

interface RecyclerViewListener<T1, T3> {
    fun onClickItemForViewId(position: T1, clickedView: T3, itemReplyView: T3)
}