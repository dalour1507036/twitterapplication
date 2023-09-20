package com.example.twitterapplication.controller.api.v1

import com.example.twitterapplication.config.FriendshipStatus
import com.example.twitterapplication.controller.api.BaseController
import com.example.twitterapplication.dto.TwitterUserRequest
import com.example.twitterapplication.dto.TwitterUserResponse
import com.example.twitterapplication.dto.TwitterUserSearchResponse
import com.example.twitterapplication.exception.UserNotFound
import com.example.twitterapplication.mapper.toTwitterUser
import com.example.twitterapplication.mapper.toTwitterUserResponse
import com.example.twitterapplication.service.TwitterFriendRequestService
import com.example.twitterapplication.service.TwitterUserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/api/v1/users")
class V1TwitterUserController(
        private val twitterUserService: TwitterUserService,
        private val twitterFriendRequestService: TwitterFriendRequestService
)
    : BaseController(){
    @PostMapping
    fun userCreate(
            @RequestBody twitterUserRequest: TwitterUserRequest
    ): ResponseEntity<TwitterUserResponse> {
        val createdUser =
                twitterUserService
                        .createTwitterUser(twitterUserRequest.toTwitterUser())
                        .toTwitterUserResponse()
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser)
    }

    @GetMapping
    fun userPage(): ResponseEntity<List<TwitterUserResponse>> {
        val twitterUsersLists: List<TwitterUserResponse> =
            twitterUserService
                .getAllTwitterUsers()
                .map { twitterUser ->
                    twitterUser.toTwitterUserResponse()
                }
        return ResponseEntity.status(HttpStatus.OK).body(twitterUsersLists)
    }

    @GetMapping("/{userId}")
    fun getTwitterUserById(
        @PathVariable userId: Long
        ): ResponseEntity<TwitterUserResponse> {
        val twitterUserResponse =
            twitterUserService
                .getTwitterUserById(userId)
                .toTwitterUserResponse()
        return ResponseEntity.status(HttpStatus.OK).body(twitterUserResponse)
    }

    @GetMapping("/search")
    fun getAUser(@RequestParam(required = true) email: String):
            ResponseEntity<TwitterUserSearchResponse> {
        val userObj = twitterUserService.getTwitterUserById(currentUserId())
        val friendObj = twitterUserService.getTwitterUserByEmail(email) ?: throw UserNotFound("user with email - $email not found")

        val status = when {
            twitterFriendRequestService.getAFriend(userObj, friendObj) != null -> FriendshipStatus.friend
            twitterFriendRequestService.getIncoming(userObj, friendObj) != null -> FriendshipStatus.received
            twitterFriendRequestService.getAOutgoing(userObj, friendObj) != null -> FriendshipStatus.sent
            else -> FriendshipStatus.none
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(TwitterUserSearchResponse(friendObj.toTwitterUserResponse(), status.toString()))

    }


}