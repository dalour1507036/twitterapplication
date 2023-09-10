package com.example.twitterapplication.controller.api.v1

import com.example.twitterapplication.controller.api.BaseController
import com.example.twitterapplication.dto.TwitterCommentRequest
import com.example.twitterapplication.dto.TwitterCommentResponse
import com.example.twitterapplication.mapper.toTwitterComment
import com.example.twitterapplication.mapper.toTwitterCommentResponse
import com.example.twitterapplication.service.TwitterCommentService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/twitter-app/comments")
class V1TwitterCommentController(private val twitterCommentService: TwitterCommentService)
    : BaseController() {
    @PostMapping("/posts/{postId}")
    fun createTwitterUserCommentInTwitterPost(
        @PathVariable postId: Long,
        @RequestBody twitterCommentRequest: TwitterCommentRequest
        ): ResponseEntity<TwitterCommentResponse> {
        val createdTwitterComment = twitterCommentService.createTwitterUserCommentInTwitterPost(
            twitterCommentRequest.toTwitterComment(),
            currentUserId(),
            postId
        ).toTwitterCommentResponse()
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTwitterComment)
        }
    }