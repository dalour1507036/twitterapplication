package com.example.twitterapplication.controller.api.v1

import com.example.twitterapplication.config.FriendshipStatus
import com.example.twitterapplication.controller.api.BaseController
import com.example.twitterapplication.dto.TwitterUserRequest
import com.example.twitterapplication.dto.TwitterUserResponse
import com.example.twitterapplication.dto.TwitterUserSearchResponse
import com.example.twitterapplication.exceptionhandler.exceptions.UserNotFound
import com.example.twitterapplication.mapper.toTwitterUser
import com.example.twitterapplication.mapper.toTwitterUserResponse
import com.example.twitterapplication.model.TwitterFriendRequest
import com.example.twitterapplication.service.TwitterFriendRequestService
import com.example.twitterapplication.service.TwitterUserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@CrossOrigin("*")
@Controller
@RequestMapping("/api/v1/users")
class V1TwitterUserController(
        private val twitterUserService: TwitterUserService,
        private val twitterFriendRequestService: TwitterFriendRequestService
) : BaseController(){
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

    @PutMapping
    fun updateTwitterUser(@RequestBody twitterUserRequest: TwitterUserRequest)
    : ResponseEntity<TwitterUserResponse> {
        val userId = currentUserId()
        val twitterUserResponse = twitterUserService.updateTwitterUser(
            userId,
            twitterUserRequest.toTwitterUser()
        ).toTwitterUserResponse()
        return ResponseEntity.status(HttpStatus.OK).body(twitterUserResponse)
    }

    @DeleteMapping
    fun deleteTwitterUser(): ResponseEntity<String> {
        twitterUserService.deleteTwitterUserById(currentUserId())
        return ResponseEntity.status(HttpStatus.OK).body("User with  id ${currentUserId()} deleted successfully")
    }

    @GetMapping("/search")
    fun getAUser(@RequestParam(required = true) email: String):
            ResponseEntity<TwitterUserSearchResponse> {
        val userObj = twitterUserService.getTwitterUserById(currentUserId())
        val friendObj = twitterUserService.getTwitterUserByEmail(email) ?: throw UserNotFound("user with email - $email doesn't exist")
        var status: String = ""
        var friendRequestInstance = TwitterFriendRequest()

        if (twitterFriendRequestService.getFriend(userObj, friendObj) != null) {
            status = FriendshipStatus.Friend.toString()
            friendRequestInstance = twitterFriendRequestService.getFriend(userObj, friendObj)!!
        }
        else if (twitterFriendRequestService.getIncoming(userObj, friendObj) != null) {
            status = FriendshipStatus.Received.toString()
            friendRequestInstance = twitterFriendRequestService.getIncoming(userObj, friendObj) !!
        }
        else if (twitterFriendRequestService.getOutgoing(userObj, friendObj) != null) {
            status = FriendshipStatus.Sent.toString()
            friendRequestInstance = twitterFriendRequestService.getOutgoing(userObj, friendObj)!!
        }
        else {
            status = FriendshipStatus.None.toString()
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(TwitterUserSearchResponse(
                    friendObj.toTwitterUserResponse(),
                    friendRequestInstance.id,
                    status
                ))

    }


}