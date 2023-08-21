package com.example.twitterapplication.service

import com.example.twitterapplication.dto.TwitterCommentDto


interface TwitterCommentService {
    fun createTwitterUserCommentInTwitterPost(
        twitterCommentDto: TwitterCommentDto,
        userId: Long,
        postId: Long
        ): TwitterCommentDto
}