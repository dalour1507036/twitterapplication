package com.example.twitterapplication.dto

data class TwitterCommentResponse(
    var commentId: Long ,
    var commentContent: String,
    var user: TwitterUserResponse
)
