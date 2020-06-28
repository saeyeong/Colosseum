package com.sae.colosseum.interfaces

interface RecyclerViewListener<T1, T2, T3> {
    fun onClickItem(clickedView: T1, position: T2, itemView: T3)
}