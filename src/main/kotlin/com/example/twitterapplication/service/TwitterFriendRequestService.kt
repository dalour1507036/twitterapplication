package com.example.twitterapplication.service

import com.example.twitterapplication.dto.FriendRequestAcceptedResponse
import com.example.twitterapplication.model.TwitterFriendRequest
import com.example.twitterapplication.model.TwitterUser

interface TwitterFriendRequestService {
    fun send(senderId: Long, receiverId: Long): TwitterFriendRequest
    fun accept(friendRequestId: Long): TwitterFriendRequest
    fun getAllIncoming(userId: Long): List<TwitterFriendRequest>
    fun getAllOutgoing(userId: Long): List<TwitterFriendRequest>
//    fun canSend(userId: Long): Set<TwitterUser?>
    fun getAllFriends(userId: Long): List<FriendRequestAcceptedResponse>
    fun getFriend(userObj:TwitterUser, friendObj: TwitterUser): TwitterFriendRequest?
    fun getIncoming(userObj:TwitterUser, friendObj: TwitterUser): TwitterFriendRequest?
    fun getOutgoing(userObj:TwitterUser, friendObj: TwitterUser): TwitterFriendRequest?
    fun deleteFriendRequestInstanceById(friendRequestId: Long)

}