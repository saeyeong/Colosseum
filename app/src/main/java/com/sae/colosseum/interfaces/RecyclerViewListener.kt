package com.sae.colosseum.interfaces

interface RecyclerViewListener<T1, T3> {
    fun onClickItem(position: T1, clickedView: T3, itemView: T3)
}