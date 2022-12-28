package com.example.lostfoundserver.entity

import java.time.LocalDateTime
import java.util.*

@Suppress("unused")
data class Post(
    var id: Int = 0,
    var name: String = "",
    var owner: Int = 0,
    var type: Int = PostType.TYPE_LOST.ordinal,
    var description: String = "",
    var image: String = "",
    var resolved: Boolean = false,
    var time: LocalDateTime = LocalDateTime.now()
) {
    fun getTypeReal() = PostType.values()[type]
    fun setTypeReal(type: PostType) {
        this.type = type.ordinal
    }
}
