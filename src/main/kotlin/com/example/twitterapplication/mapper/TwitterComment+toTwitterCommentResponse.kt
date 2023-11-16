package com.example.twitterapplication.mapper

import com.example.twitterapplication.dto.TwitterCommentResponse
import com.example.twitterapplication.model.TwitterComment

fun TwitterComment.toTwitterCommentResponse(): TwitterCommentResponse {
    return TwitterCommentResponse(
        commentId = this.id,
        commentContent = this.commentContent,
        user = this.twitterUser.toTwitterUserResponse()
    )
}