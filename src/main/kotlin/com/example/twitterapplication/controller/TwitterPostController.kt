package com.example.twitterapplication.controller

import com.example.twitterapplication.dto.TwitterPostRequest
import com.example.twitterapplication.dto.TwitterPostResponse
import com.example.twitterapplication.mapper.toTwitterCommentResponse
import com.example.twitterapplication.mapper.toTwitterPost
import com.example.twitterapplication.mapper.toTwitterPostResponse
import com.example.twitterapplication.mapper.toTwitterUserResponse
import com.example.twitterapplication.service.TwitterCommentService
import com.example.twitterapplication.service.TwitterPostService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/twitter-app/")
class TwitterPostController(
    private val twitterPostService: TwitterPostService,
    private val twitterCommentService: TwitterCommentService
): BaseController() {
    @PostMapping("/posts")
    fun createTwitterPost(
        @RequestBody twitterPostRequest: TwitterPostRequest): ResponseEntity<TwitterPostResponse> {
        val createdTwitterPost = twitterPostService.createTwitterPost(
            twitterPostRequest.toTwitterPost(),
            currentUserId()
        ).toTwitterPostResponse()
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTwitterPost)
    }

    @GetMapping("/posts")
    fun getAllTwitterPostsByUserId(): ResponseEntity<List<TwitterPostResponse>> {
        val allTwitterPostsResponse = twitterPostService.getAllTwitterPostsByUserId(currentUserId())
            .map { twitterPost -> twitterPost.toTwitterPostResponse() }

        return ResponseEntity.status(HttpStatus.OK).body(allTwitterPostsResponse)
    }

    @GetMapping("/posts/{postId}")
    fun getTwitterPostById(@PathVariable postId: Long): ResponseEntity<TwitterPostResponse> {
        val twitterPost = twitterPostService.getTwitterPostById(postId)
        val allCommentsInAPost = twitterCommentService.getTwitterCommentsByTwitterPost(twitterPost)

        val allCommentResponseInAPost = allCommentsInAPost.map { twitterComment ->
        twitterComment.toTwitterCommentResponse()
        }

        val twitterPostResponse =  TwitterPostResponse(
        twitterPost.twitterPostContent,
        twitterPost.twitterUser?.toTwitterUserResponse(),
        allCommentResponseInAPost
        )
        return ResponseEntity.status(HttpStatus.OK).body(twitterPostResponse)
    }
}