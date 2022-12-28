package com.example.lostfoundserver.entity

@Suppress("unused")
data class FileEntry(
    var id: Int = 0,
    var owner: Int = 0,
    var filename: String = "",
    var size: Int = 0,
    var key: String = ""
)