package com.example.twitterapplication.controller.api.v1

import com.example.twitterapplication.controller.api.BaseController
import com.example.twitterapplication.dto.TwitterUserResponse
import com.example.twitterapplication.mapper.toTwitterUserResponse
import com.example.twitterapplication.service.TwitterFriendRequestService
import com.example.twitterapplication.service.TwitterUserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/friends")
class V1TwitterFriendController(
        private val twitterFriendRequestService: TwitterFriendRequestService,
        private val twitterUserService: TwitterUserService
) : BaseController() {
    @GetMapping("/send")
    fun send(@PathVariable receiverId: Long): ResponseEntity<String> {
        twitterFriendRequestService.send(currentUserId(),receiverId)
        return ResponseEntity.status(HttpStatus.CREATED).body("Request Sent")
    }

    @GetMapping("/accept/{friendRequestId}")
    fun receiveFriendRequest(@PathVariable friendRequestId: Long): ResponseEntity<String> {
        twitterFriendRequestService.accept(friendRequestId)
        return ResponseEntity.status(HttpStatus.CREATED).body("Request Received")
    }

    @GetMapping("/sent")
    fun getAllOutgoing(): ResponseEntity<List<TwitterUserResponse>> {
        val allOutgoing = twitterFriendRequestService.getAllOutgoing(currentUserId())
        return ResponseEntity.status(HttpStatus.OK).body(
                allOutgoing.map { twitterFriendRequest ->
                            twitterFriendRequest.receiver.toTwitterUserResponse()
                        }
        )
    }

    @GetMapping("/received")
    fun getAllIncoming(): ResponseEntity<List<TwitterUserResponse>> {
        val allIncoming = twitterFriendRequestService.getAllIncoming(currentUserId())
        return ResponseEntity.status(HttpStatus.OK).body(
                allIncoming.map { twitterFriendRequest ->
                            twitterFriendRequest.sender.toTwitterUserResponse()
                        }
        )
    }

    @GetMapping("/search")
    fun getAFriend(@RequestParam(required = true) email: String):
            ResponseEntity<TwitterUserResponse> {
        val friendObj = twitterUserService.getTwitterUserByEmail(email)
        val userObj = twitterUserService.getTwitterUserById(currentUserId())

        return ResponseEntity.status(HttpStatus.OK).body(twitterFriendRequestService
                .getAFriend(userObj, friendObj!!)?.toTwitterUserResponse()
        )
    }

    @GetMapping("/all")
    fun getAllFriends(): List<TwitterUserResponse> {
        return twitterFriendRequestService
                .getAllFriends(currentUserId())
                .map { twitterUser -> twitterUser.toTwitterUserResponse()  }
    }
}