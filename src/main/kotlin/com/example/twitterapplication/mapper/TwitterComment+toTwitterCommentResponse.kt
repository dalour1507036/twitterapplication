package com.example.twitterapplication.mapper

import com.example.twitterapplication.dto.TwitterCommentResponse
import com.example.twitterapplication.model.TwitterComment

fun TwitterComment.toTwitterCommentResponse(): TwitterCommentResponse {
    return TwitterCommentResponse(
        commentContent = this.commentContent
    )
}