package com.sae.colosseum.interfaces

interface RecyclerViewListener {
    fun onClick(position: Int, item: Any)
    fun onLongClick(position: Int, item: Any)
    fun onClickItemForViewId(position: Int, item: Any, viewId: Int)
}