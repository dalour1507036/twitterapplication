package com.example.twitterapplication.mapper

import com.example.twitterapplication.dto.TwitterPostRequest
import com.example.twitterapplication.model.TwitterPost


fun TwitterPostRequest.toTwitterPost(): TwitterPost {
    val entity = TwitterPost()
    entity.twitterPostContent = this.twitterPostContent
    return entity
}