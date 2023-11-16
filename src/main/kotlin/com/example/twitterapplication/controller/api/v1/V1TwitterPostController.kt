package com.example.twitterapplication.controller.api.v1

import com.example.twitterapplication.controller.api.BaseController
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

@CrossOrigin(origins = ["http://localhost:3000"])
@RestController
@RequestMapping("/api/v1/posts")
class V1TwitterPostController(
    private val twitterPostService: TwitterPostService,
    private val twitterCommentService: TwitterCommentService
): BaseController() {
    @PostMapping
    fun createTwitterPost(
        @RequestBody twitterPostRequest: TwitterPostRequest): ResponseEntity<TwitterPostResponse> {
        val createdTwitterPost = twitterPostService.createTwitterPost(
            twitterPostRequest.toTwitterPost(),
            currentUserId()
        ).toTwitterPostResponse()
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTwitterPost)
    }
//    @Transactional
    @GetMapping
    fun getAllTwitterPostsByUserId(): ResponseEntity<List<TwitterPostResponse>> {
        val allTwitterPostsResponse = twitterPostService.getAllTwitterPostsByUserId(currentUserId())
            .map { twitterPost -> twitterPost.toTwitterPostResponse() }
        return ResponseEntity.status(HttpStatus.OK).body(allTwitterPostsResponse)
    }

    @GetMapping("/all")
    fun getAllTwitterPosts(): ResponseEntity<List<TwitterPostResponse>> {
        val allTwitterPostResponse = twitterPostService.getAllTwitterPosts()
            .map { twitterPost -> twitterPost.toTwitterPostResponse()  }
        return ResponseEntity.status(HttpStatus.OK).body(allTwitterPostResponse)
    }

    @PutMapping("/{postId}")
    fun updateTwitterPost(
        @PathVariable postId: Long,
        @RequestBody twitterPostRequest: TwitterPostRequest
        ): ResponseEntity<TwitterPostResponse> {
        val updatedTwitterPostResponse = twitterPostService.updateTwitterPost(
            postId, twitterPostRequest.toTwitterPost()
        ).toTwitterPostResponse()
        return ResponseEntity.status(HttpStatus.OK).body(updatedTwitterPostResponse)
    }

    @DeleteMapping("/{postId}")
    fun deleteTwitterPost(@PathVariable postId: Long): ResponseEntity<String> {
        twitterPostService.deleteTwitterPostById(postId)
        return ResponseEntity.status(HttpStatus.OK).body("Post deleted successfully")
    }

    @GetMapping("/{postId}")
    fun getTwitterPostById(@PathVariable postId: Long): ResponseEntity<TwitterPostResponse> {
        val twitterPost = twitterPostService.getTwitterPostById(postId)
        val allCommentsInAPost = twitterCommentService.getTwitterCommentsByTwitterPost(twitterPost)

        val allCommentResponseInAPost = allCommentsInAPost.map { twitterComment ->
            twitterComment.toTwitterCommentResponse()
        }

        val twitterPostResponse =  TwitterPostResponse(
            twitterPost.id,
            twitterPost.twitterPostContent,
            twitterPost.twitterUser?.toTwitterUserResponse(),
            allCommentResponseInAPost
        )
        return ResponseEntity.status(HttpStatus.OK).body(twitterPostResponse)
    }
}