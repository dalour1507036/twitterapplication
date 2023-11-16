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

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/comments")
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

    @PutMapping("/{commentId}")
    fun updateTwitterComment(
        @PathVariable commentId:Long,
        @RequestBody twitterCommentRequest: TwitterCommentRequest
    ): ResponseEntity<TwitterCommentResponse> {
        val updatedTwitterCommentResponse = twitterCommentService.updateTwitterComment(
            commentId,
            twitterCommentRequest.toTwitterComment()
        ).toTwitterCommentResponse()
        return ResponseEntity.status(HttpStatus.OK).body(updatedTwitterCommentResponse)
    }

    @DeleteMapping("/{commentId}")
    fun deleteTwitterComment(@PathVariable commentId: Long): ResponseEntity<String> {
        twitterCommentService.deleteTwitterComment(commentId)
        return ResponseEntity.status(HttpStatus.OK).body("Comment delete successfully")
    }
}