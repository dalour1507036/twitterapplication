package com.example.twitterapplication.service

import com.example.twitterapplication.model.TwitterComment
import com.example.twitterapplication.model.TwitterPost


interface TwitterCommentService {
    fun createTwitterUserCommentInTwitterPost(
        twitterComment: TwitterComment,
        userId: Long,
        postId: Long
    ): TwitterComment
    fun getTwitterCommentsByTwitterPost(
        twitterPost: TwitterPost
    ): List<TwitterComment>

    fun updateTwitterComment(
        commentId: Long,
        updatedTwitterComment: TwitterComment
    ): TwitterComment
    fun deleteTwitterComment(commentId: Long)
}