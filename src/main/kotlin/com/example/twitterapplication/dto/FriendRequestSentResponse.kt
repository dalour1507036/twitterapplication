package com.example.twitterapplication.dto

data class FriendRequestSentResponse (
    var friendRequestId: Long,
    var senderId: Long,
    var receiver: TwitterUserResponse,
    var accepted: Boolean
)