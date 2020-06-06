package com.sae.colosseum.utils

import com.sae.colosseum.model.entity.ResponseEntity

interface ResultInterface<T> {
    fun result(value: T)
}