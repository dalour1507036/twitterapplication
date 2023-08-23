package com.example.twitterapplication.controller

import com.example.twitterapplication.dto.TwitterUserRequest
import com.example.twitterapplication.dto.TwitterUserResponse
import com.example.twitterapplication.mapper.toTwitterUser
import com.example.twitterapplication.mapper.toTwitterUserResponse
import com.example.twitterapplication.service.TwitterUserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/twitter-app")
class TwitterUserController(private val twitterUserService: TwitterUserService) {
    @GetMapping
    fun home(): ResponseEntity<String> {
       return ResponseEntity.status(HttpStatus.OK).body("hello from the home url")
    }

    @GetMapping("/users")
    fun userPage(): ResponseEntity<List<TwitterUserResponse>> {
        val twitterUsersLists: List<TwitterUserResponse> =
            twitterUserService
                .getAllTwitterUsers()
                .map { twitterUser ->
                    twitterUser.toTwitterUserResponse()
                }
        return ResponseEntity.status(HttpStatus.OK).body(twitterUsersLists)
    }

    @GetMapping("users/{userId}")
    fun getTwitterUserById(@PathVariable userId: Long): ResponseEntity<TwitterUserResponse> {
        val twitterUserResponse =
            twitterUserService
                .getTwitterUserById(userId)
                .toTwitterUserResponse()
        return ResponseEntity.status(HttpStatus.OK).body(twitterUserResponse)
    }
    @PostMapping("/users")
    fun userCreate(
        @RequestBody twitterUserRequest: TwitterUserRequest
    ): ResponseEntity<TwitterUserResponse> {
        val createdUser =
            twitterUserService
                .createTwitterUser(twitterUserRequest.toTwitterUser())
                .toTwitterUserResponse()
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser)
    }
}