package com.example.instaapp.model

data class AddPostRequest(
    var title: String? = null,
    var description: String? = null,
    var image: Image? = null,
    var location: String? = null,
    var userId: String? = null,
)