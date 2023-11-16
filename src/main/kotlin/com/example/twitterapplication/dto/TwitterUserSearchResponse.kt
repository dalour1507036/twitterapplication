package com.example.twitterapplication.dto

data class TwitterUserSearchResponse (
        var twitterUserResponse: TwitterUserResponse,
        var friendRequestId: Long,
        var friendShipStatus: String?
)

