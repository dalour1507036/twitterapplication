package com.example.twitterapplication.mapper

import com.example.twitterapplication.dto.TwitterPostResponse
import com.example.twitterapplication.model.TwitterPost

//done
fun TwitterPost.toTwitterPostResponse(): TwitterPostResponse {
    return TwitterPostResponse(
        twitterPostContent = this.twitterPostContent,
        twitterUser = this.twitterUser?.toTwitterUserResponse(),
        twitterUserComments = this.twitterComments
            .map { twitterComment ->
            twitterComment.toTwitterCommentResponse()
        }
    )
}
