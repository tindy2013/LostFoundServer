package com.example.lostfoundserver.entity

data class PostView(
    var id: Int,
    var owner: Int,
    var name: String,
    var type: PostType,
    var image: String,
    var description: String,
    var contactName: String,
    var contactNumber: String,
    var resolved: Boolean,
    var time: String
)