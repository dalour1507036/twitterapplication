package com.example.twitterapplication.controller

import com.example.twitterapplication.dto.TwitterPostRequest
import com.example.twitterapplication.dto.TwitterPostResponse
import com.example.twitterapplication.security.TwitterUserPrincipal
import com.example.twitterapplication.service.TwitterPostService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/twitter-app/")
class TwitterPostController(private val twitterPostService: TwitterPostService) {
    @PostMapping("/posts")
    fun createTwitterPost(
        @AuthenticationPrincipal twitterUserPrincipal: TwitterUserPrincipal,
        @RequestBody twitterPostRequest: TwitterPostRequest
    ): ResponseEntity<TwitterPostResponse> {
        val createdTwitterPost = twitterPostService.createTwitterPost(
            twitterPostRequest,
            twitterUserPrincipal.getTwitterUserId()
        )
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTwitterPost)
    }

    @GetMapping("/posts")
    fun getAllTwitterPostsByUserId(
        @AuthenticationPrincipal twitterUserPrincipal: TwitterUserPrincipal
    ): ResponseEntity<List<TwitterPostResponse>> {
        val allTwitterPosts = twitterPostService.getAllTwitterPostsByUserId(
            twitterUserPrincipal.getTwitterUserId()
        )
        return ResponseEntity.status(HttpStatus.OK).body(allTwitterPosts)
    }

//    @GetMapping("/posts")
//    fun getAllTwitterUserPosts(): List<TwitterPost> {
//        return twitterPostService.getAllTwitterPosts()
//    }
    @GetMapping("/posts/{postId}")
    fun getTwitterPostById(@PathVariable postId: Long): ResponseEntity<TwitterPostResponse> {
        val twitterPostDto = twitterPostService.getTwitterPostById(postId)
    return ResponseEntity.status(HttpStatus.OK).body(twitterPostDto)
    }
}