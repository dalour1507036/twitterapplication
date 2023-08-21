package com.example.twitterapplication.controller

import com.example.twitterapplication.dto.TwitterUserDto
import com.example.twitterapplication.model.TwitterUser
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
    fun userPage(): ResponseEntity<List<TwitterUserDto>> {
        val twitterUsersLists: List<TwitterUserDto> = twitterUserService.getAllTwitterUsers()
        return ResponseEntity.status(HttpStatus.OK).body(twitterUsersLists)
    }

    @GetMapping("users/{userId}")
    fun getTwitterUserById(@PathVariable userId: Long): ResponseEntity<TwitterUserDto> {
        val twitterUserDto: TwitterUserDto = twitterUserService.getTwitterUserById(userId)
        return ResponseEntity.status(HttpStatus.OK).body(twitterUserDto)
    }
    @PostMapping("/users")
    fun userCreate(@RequestBody twitterUser: TwitterUser): ResponseEntity<TwitterUser> {
        val createdUser: TwitterUser = twitterUserService.createTwitterUser(twitterUser)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser)
    }
}