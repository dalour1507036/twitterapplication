package com.example.twitterapplication.dto

data class FriendRequestReceivedResponse(
    var friendRequestId: Long,
    var sender: TwitterUserResponse,
    var receiverId: Long,
    var accepted: Boolean
)
