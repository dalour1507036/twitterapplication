package com.example.twitterapplication.repository

import com.example.twitterapplication.model.TwitterFriendRequest
import com.example.twitterapplication.model.TwitterUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TwitterFriendRequestRepo : JpaRepository<TwitterFriendRequest, Long>{
    fun findByReceiverAndAcceptedFalse(twitterUser: TwitterUser): List<TwitterFriendRequest>
    fun findBySenderAndAcceptedFalse(twitterUser: TwitterUser): List<TwitterFriendRequest>
    fun findByReceiverAndAcceptedTrue(twitterUser: TwitterUser): List<TwitterFriendRequest>
    fun findBySenderAndAcceptedTrue(twitterUser: TwitterUser): List<TwitterFriendRequest>
    fun findBySenderAndReceiverAndAcceptedTrue(
            userObj: TwitterUser,
            friendObj: TwitterUser
    ): TwitterFriendRequest?
    fun findByReceiverAndSenderAndAcceptedTrue(
            userObj: TwitterUser,
            friendObj: TwitterUser
    ): TwitterFriendRequest?
    fun findBySenderAndReceiverAndAcceptedFalse(
            userObj: TwitterUser,
            friendObj: TwitterUser
    ): TwitterFriendRequest?
    fun findByReceiverAndSenderAndAcceptedFalse(
            userObj: TwitterUser,
            friendObj: TwitterUser
    ): TwitterFriendRequest?
}