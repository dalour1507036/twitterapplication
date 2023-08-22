package com.example.twitterapplication.dto

class TwitterPostResponse(
    var twitterPostContent: String,
    var twitterUser: TwitterUserResponse?,
    var twitterUserComments: List<TwitterCommentResponse> = arrayListOf()
)