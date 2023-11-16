package com.example.twitterapplication.service.imp

import com.example.twitterapplication.dto.FriendRequestAcceptedResponse
import com.example.twitterapplication.mapper.toTwitterUserResponse
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

    override fun deleteFriendRequestInstanceById(friendRequestId: Long) {
        twitterFriendRequestRepo.deleteById(friendRequestId)
    }

    override fun getAllIncoming(userId: Long): List<TwitterFriendRequest> {
        return twitterFriendRequestRepo
                .findByReceiverAndAcceptedFalse(twitterUserRepo.findById(userId).orElse(null))
    }

    override fun getAllOutgoing(userId: Long): List<TwitterFriendRequest> {
        return twitterFriendRequestRepo
                .findBySenderAndAcceptedFalse(twitterUserRepo.findById(userId).orElse(null))
    }

    override fun getIncoming(userObj:TwitterUser, friendObj: TwitterUser): TwitterFriendRequest? {
        return twitterFriendRequestRepo
                .findByReceiverAndSenderAndAcceptedFalse(userObj, friendObj)
    }

    override fun getOutgoing(userObj:TwitterUser, friendObj: TwitterUser): TwitterFriendRequest? {
       return twitterFriendRequestRepo
                .findBySenderAndReceiverAndAcceptedFalse(userObj, friendObj)
    }

    override fun getFriend(userObj:TwitterUser, friendObj: TwitterUser): TwitterFriendRequest? {
        val friendCase1 =
                twitterFriendRequestRepo
                        .findBySenderAndReceiverAndAcceptedTrue(userObj, friendObj)

        val friendCase2 =  twitterFriendRequestRepo
                         .findByReceiverAndSenderAndAcceptedTrue(userObj, friendObj)

        if (friendCase1 != null){
            return friendCase1
        }
        return friendCase2


//    duplicate rows query
//        return twitterFriendRequestRepo
//                .findBySenderAndReceiverAndAcceptedTrue(
//                        userObj, friendObj
//                )?.receiver
//
//
    }

//    override fun canSend(userId: Long): Set<TwitterUser?> {
//        val allIncomingFriendRequestsNotAccepted =
//                getAllIncoming(userId)
//                        .map { twitterFriendRequest -> twitterFriendRequest.sender }
//
//        val allOutgoingFriendRequestsNotAccepted =
//                getAllOutgoing(userId)
//                        .map { twitterFriendRequest -> twitterFriendRequest.receiver }
//
//        val allFriends = getAllFriends(userId)
//        return twitterUserRepo.findAll()
//                .minus(listOf(twitterUserRepo.findById(userId).orElse(null)).toSet())
//                .minus((allFriends+allOutgoingFriendRequestsNotAccepted +
//                        allIncomingFriendRequestsNotAccepted +
//                        allFriends).toSet()).toSet()
//    }

    override fun getAllFriends(userId: Long): List<FriendRequestAcceptedResponse> {
        val twitterUser = twitterUserRepo.findById(userId).orElse(null)
        val friendsFromIncomingRequests = twitterFriendRequestRepo
                .findByReceiverAndAcceptedTrue(twitterUser)
                .map { FriendRequestAcceptedResponse(
                    it.id,
                    it.sender.toTwitterUserResponse()
                ) }

        val friendsFromOutgoingRequests = twitterFriendRequestRepo
                .findBySenderAndAcceptedTrue(twitterUser)
                .map { FriendRequestAcceptedResponse(
                    it.id,
                    it.receiver.toTwitterUserResponse()
                ) }
        return friendsFromIncomingRequests + friendsFromOutgoingRequests
    }
}