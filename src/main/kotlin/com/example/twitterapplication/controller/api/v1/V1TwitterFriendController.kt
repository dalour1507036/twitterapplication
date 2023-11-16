package com.example.twitterapplication.controller.api.v1

import com.example.twitterapplication.controller.api.BaseController
import com.example.twitterapplication.dto.FriendRequestAcceptedResponse
import com.example.twitterapplication.dto.FriendRequestReceivedResponse
import com.example.twitterapplication.dto.FriendRequestSentResponse
import com.example.twitterapplication.mapper.toFriendRequestReceivedResponse
import com.example.twitterapplication.mapper.toFriendRequestSentResponse
import com.example.twitterapplication.service.TwitterFriendRequestService
import com.example.twitterapplication.service.TwitterUserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/friends")
class V1TwitterFriendController(
        private val twitterFriendRequestService: TwitterFriendRequestService,
        private val twitterUserService: TwitterUserService
) : BaseController() {
    @PostMapping("/send/{receiverId}")
    fun send(@PathVariable receiverId: Long): ResponseEntity<String> {
        twitterFriendRequestService.send(currentUserId(),receiverId)
        return ResponseEntity.status(HttpStatus.CREATED).body("Request Sent")
    }

    @PutMapping("/accept/{friendRequestId}")
    fun receiveFriendRequest(@PathVariable friendRequestId: Long): ResponseEntity<String> {
        twitterFriendRequestService.accept(friendRequestId)
        return ResponseEntity.status(HttpStatus.CREATED).body("Request Received")
    }

    @DeleteMapping("/remove/{friendRequestId}")
    fun deleteFriendInstance(@PathVariable friendRequestId: Long): ResponseEntity<String>{
        twitterFriendRequestService.deleteFriendRequestInstanceById(friendRequestId)
        return ResponseEntity.status(HttpStatus.OK).body("Instance Remove Successful")
    }

    @GetMapping("/sent")
    fun getAllOutgoing(): ResponseEntity<List<FriendRequestSentResponse>> {
        val allOutgoing = twitterFriendRequestService.getAllOutgoing(currentUserId())
        return ResponseEntity.status(HttpStatus.OK).body(
                allOutgoing.map { twitterFriendRequest ->
                            twitterFriendRequest.toFriendRequestSentResponse()
                }
        )
    }

    @GetMapping("/received")
    fun getAllIncoming(): ResponseEntity<List<FriendRequestReceivedResponse>> {
        val allIncoming = twitterFriendRequestService.getAllIncoming(currentUserId())
        return ResponseEntity.status(HttpStatus.OK).body(
                allIncoming.map { twitterFriendRequest ->
                            twitterFriendRequest.toFriendRequestReceivedResponse()
                }
        )
    }

    @GetMapping("/search")
    fun getAFriend(@RequestParam(required = true) email: String):
            ResponseEntity<FriendRequestReceivedResponse> {
        val friendObj = twitterUserService.getTwitterUserByEmail(email)
        val userObj = twitterUserService.getTwitterUserById(currentUserId())

        return ResponseEntity.status(HttpStatus.OK).body(twitterFriendRequestService
                .getFriend(userObj, friendObj!!)?.toFriendRequestReceivedResponse()
        )
    }

    @GetMapping("/all")
    fun getAllFriends(): List<FriendRequestAcceptedResponse> {
        return twitterFriendRequestService
                .getAllFriends(currentUserId())
//                .map { twitterUser -> twitterUser.toTwitterUserResponse()  }
    }
}