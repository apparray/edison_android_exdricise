package com.example.common.mapper

interface Mapper<I, O> {

    fun from(i: I): O

    fun to(o: O): I
}
