package com.example.twitterapplication.service

import com.example.twitterapplication.dto.TwitterCommentRequest
import com.example.twitterapplication.dto.TwitterCommentResponse


interface TwitterCommentService {
    fun createTwitterUserCommentInTwitterPost(
        twitterCommentRequest: TwitterCommentRequest,
        userId: Long,
        postId: Long
        ): TwitterCommentResponse
}