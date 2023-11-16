package com.example.twitterapplication.dto

data class FriendRequestAcceptedResponse (
    var friendRequestId: Long,
    var friend: TwitterUserResponse
)