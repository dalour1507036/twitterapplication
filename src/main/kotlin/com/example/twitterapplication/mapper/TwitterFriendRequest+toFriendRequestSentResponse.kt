package com.example.twitterapplication.mapper

import com.example.twitterapplication.dto.FriendRequestSentResponse
import com.example.twitterapplication.model.TwitterFriendRequest

fun TwitterFriendRequest.toFriendRequestSentResponse(): FriendRequestSentResponse {
    return FriendRequestSentResponse(
        friendRequestId = this.id,
        senderId = this.sender.id,
        receiver = this.receiver.toTwitterUserResponse(),
        accepted = this.accepted
    )
}