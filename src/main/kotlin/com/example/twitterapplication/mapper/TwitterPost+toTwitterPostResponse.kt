package com.example.twitterapplication.mapper

import com.example.twitterapplication.dto.TwitterPostResponse
import com.example.twitterapplication.model.TwitterPost

fun TwitterPost.toTwitterPostResponse(): TwitterPostResponse {
    return TwitterPostResponse(
        postId = this.id,
        twitterPostContent = this.twitterPostContent,
        twitterUser = this.twitterUser?.toTwitterUserResponse(),
        twitterUserComments = this.twitterComments
            .map { twitterComment ->
            twitterComment.toTwitterCommentResponse()
        }
    )
}
