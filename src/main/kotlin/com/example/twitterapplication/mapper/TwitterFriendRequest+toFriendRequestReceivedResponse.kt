package com.example.twitterapplication.mapper

import com.example.twitterapplication.dto.FriendRequestReceivedResponse
import com.example.twitterapplication.model.TwitterFriendRequest

fun TwitterFriendRequest.toFriendRequestReceivedResponse(): FriendRequestReceivedResponse {
    return FriendRequestReceivedResponse(
        friendRequestId = this.id,
        sender = this.sender.toTwitterUserResponse(),
        receiverId = this.receiver.id,
        accepted = this.accepted
    )
}