package com.example.twitterapplication.controller

import com.example.twitterapplication.dto.TwitterPostDto
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
        @RequestBody twitterPostDto: TwitterPostDto
    ): ResponseEntity<TwitterPostDto> {
        val createdTwitterPost = twitterPostService.createTwitterPost(
            twitterPostDto,
            twitterUserPrincipal.getTwitterUserId()
        )
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTwitterPost)
    }

    @GetMapping("/posts")
    fun getAllTwitterPostsByUserId(
        @AuthenticationPrincipal twitterUserPrincipal: TwitterUserPrincipal
    ): ResponseEntity<List<TwitterPostDto>> {
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
    fun getTwitterPostById(@PathVariable postId: Long): ResponseEntity<TwitterPostDto> {
        val twitterPostDto = twitterPostService.getTwitterPostById(postId)
    return ResponseEntity.status(HttpStatus.OK).body(twitterPostDto)
    }
}