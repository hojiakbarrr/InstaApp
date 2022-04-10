package com.example.instaapp.model

data class Post(
    var objectId: String? = null,
    var title: String? = null,
    var description: String? = null,
    var image: Image? = null,
    var location: String? = null,
)
