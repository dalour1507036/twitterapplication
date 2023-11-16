package com.example.twitterapplication.dto

class TwitterPostResponse(
    var postId: Long,
    var twitterPostContent: String,
    var twitterUser: TwitterUserResponse?,
    var twitterUserComments: List<TwitterCommentResponse> = arrayListOf()
)