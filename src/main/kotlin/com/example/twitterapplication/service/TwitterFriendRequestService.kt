package com.example.twitterapplication.service

import com.example.twitterapplication.model.TwitterFriendRequest
import com.example.twitterapplication.model.TwitterUser

interface TwitterFriendRequestService {
    fun send(senderId: Long, receiverId: Long): TwitterFriendRequest
    fun accept(friendRequestId: Long): TwitterFriendRequest
    fun getAllIncoming(userId: Long): List<TwitterFriendRequest>
    fun getAllOutgoing(userId: Long): List<TwitterFriendRequest>
    fun canSend(userId: Long): Set<TwitterUser?>
    fun getAllFriends(userId: Long): List<TwitterUser>
    fun getAFriend(userObj:TwitterUser, friendObj: TwitterUser): TwitterUser?
    fun getIncoming(userObj:TwitterUser, friendObj: TwitterUser): TwitterFriendRequest?
    fun getAOutgoing(userObj:TwitterUser, friendObj: TwitterUser): TwitterFriendRequest?

}