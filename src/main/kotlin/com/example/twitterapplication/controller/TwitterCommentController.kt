package com.example.twitterapplication.controller

import com.example.twitterapplication.dto.TwitterCommentDto
import com.example.twitterapplication.security.TwitterUserPrincipal
import com.example.twitterapplication.service.TwitterCommentService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/twitter-app")
class TwitterCommentController(private val twitterCommentService: TwitterCommentService) {
    @PostMapping("/posts/{postId}/comments")
    fun createTwitterUserCommentInTwitterPost(
        @AuthenticationPrincipal twitterUserPrincipal: TwitterUserPrincipal,
        @PathVariable postId: Long,
        @RequestBody twitterCommentDto: TwitterCommentDto
        ): ResponseEntity<TwitterCommentDto> {

        val createdTwitterComment = twitterCommentService.createTwitterUserCommentInTwitterPost(
            twitterCommentDto,
            twitterUserPrincipal.getTwitterUserId(),
            postId
        )
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTwitterComment)
    }

}