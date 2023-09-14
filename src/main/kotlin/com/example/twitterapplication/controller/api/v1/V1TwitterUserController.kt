package com.example.twitterapplication.controller.api.v1

import com.example.twitterapplication.controller.api.BaseController
import com.example.twitterapplication.dto.TwitterUserRequest
import com.example.twitterapplication.dto.TwitterUserResponse
import com.example.twitterapplication.dto.TwitterUserSearchResponse
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
        var status: String = ""
        val userObj = twitterUserService.getTwitterUserById(currentUserId())
        val fiendObj = twitterUserService.getTwitterUserByEmail(email)
        if (twitterFriendRequestService.getAFriend(userObj, fiendObj!!) != null) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    TwitterUserSearchResponse(
                          fiendObj.toTwitterUserResponse(),
                            "friend"
                    )
            )
        }
        else if(twitterFriendRequestService.getAIncoming(userObj, fiendObj!!) != null){
            return ResponseEntity.status(HttpStatus.OK).body(
                    TwitterUserSearchResponse(
                            fiendObj.toTwitterUserResponse(),
                            "received"
                    )
            )
        }
        else if(twitterFriendRequestService.getAOutgoing(userObj, fiendObj!!) != null){
            return ResponseEntity.status(HttpStatus.OK).body(
                    TwitterUserSearchResponse(
                            fiendObj.toTwitterUserResponse(),
                            "sent"
                    )
            )
        }

        return ResponseEntity.status(HttpStatus.OK).body(
                TwitterUserSearchResponse(
                        fiendObj.toTwitterUserResponse(),
                        "sendReq"
                )
        )

    }


}