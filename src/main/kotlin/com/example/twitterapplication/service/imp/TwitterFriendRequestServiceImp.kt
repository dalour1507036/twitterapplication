package com.example.twitterapplication.service.imp

import com.example.twitterapplication.model.TwitterFriendRequest
import com.example.twitterapplication.model.TwitterUser
import com.example.twitterapplication.repository.TwitterFriendRequestRepo
import com.example.twitterapplication.repository.TwitterUserRepo
import com.example.twitterapplication.service.TwitterFriendRequestService
import org.springframework.stereotype.Service

@Service
class TwitterFriendRequestServiceImp(
        private val twitterUserRepo: TwitterUserRepo,
        private val twitterFriendRequestRepo: TwitterFriendRequestRepo
): TwitterFriendRequestService {
    override fun send(senderId: Long, receiverId: Long): TwitterFriendRequest {
        val sender = twitterUserRepo.findById(senderId).orElse(null)
        val receiver = twitterUserRepo.findById(receiverId).orElse(null)
        val twitterFriendRequest = TwitterFriendRequest()
        twitterFriendRequest.sender = sender
        twitterFriendRequest.receiver = receiver
        return twitterFriendRequestRepo.save(twitterFriendRequest)
    }

    override fun accept(friendRequestId: Long): TwitterFriendRequest {
        val twitterFriendRequest = twitterFriendRequestRepo.findById(friendRequestId).orElse(null)
        twitterFriendRequest.accepted = true
        return twitterFriendRequestRepo.save(twitterFriendRequest)
    }

    override fun getAllIncoming(userId: Long): List<TwitterFriendRequest> {
        return twitterFriendRequestRepo
                .findByReceiverAndAcceptedFalse(twitterUserRepo.findById(userId).orElse(null))
    }

    override fun getAllOutgoing(userId: Long): List<TwitterFriendRequest> {
        return twitterFriendRequestRepo
                .findBySenderAndAcceptedFalse(twitterUserRepo.findById(userId).orElse(null))
    }

    override fun getAIncoming(userObj:TwitterUser, friendObj: TwitterUser): TwitterFriendRequest? {
        return twitterFriendRequestRepo
                .findByReceiverAndSenderAndAcceptedFalse(userObj, friendObj)
    }

    override fun getAOutgoing(userObj:TwitterUser, friendObj: TwitterUser): TwitterFriendRequest? {
       return twitterFriendRequestRepo
                .findBySenderAndReceiverAndAcceptedFalse(userObj, friendObj)
    }

    override fun getAFriend(userObj:TwitterUser, friendObj: TwitterUser): TwitterUser? {
        val friendCase1 =
                twitterFriendRequestRepo
                        .findBySenderAndReceiverAndAcceptedTrue(userObj, friendObj)

        val friendCase2 =  twitterFriendRequestRepo
                         .findByReceiverAndSenderAndAcceptedTrue(userObj, friendObj)

        if (friendCase1 != null){
            return friendCase1.receiver
        }
        return friendCase2?.sender

    }

    override fun canSend(userId: Long): Set<TwitterUser?> {
        val allIncomingFriendRequestsNotAccepted =
                getAllIncoming(userId)
                        .map { twitterFriendRequest -> twitterFriendRequest.sender }

        val allOutgoingFriendRequestsNotAccepted =
                getAllOutgoing(userId)
                        .map { twitterFriendRequest -> twitterFriendRequest.receiver }

        val allFriends = getAllFriends(userId)
        return twitterUserRepo.findAll()
                .minus(listOf(twitterUserRepo.findById(userId).orElse(null)).toSet())
                .minus((allFriends+allOutgoingFriendRequestsNotAccepted +
                        allIncomingFriendRequestsNotAccepted +
                        allFriends).toSet()).toSet()
    }

    override fun getAllFriends(userId: Long): List<TwitterUser> {
        val twitterUser = twitterUserRepo.findById(userId).orElse(null)
        val friendsFromIncomingRequests = twitterFriendRequestRepo
                .findByReceiverAndAcceptedTrue(twitterUser)
                .map { it.sender }

        val friendsFromOutgoingRequests = twitterFriendRequestRepo
                .findBySenderAndAcceptedTrue(twitterUser)
                .map { it.receiver }

        return friendsFromIncomingRequests + friendsFromOutgoingRequests
    }
}