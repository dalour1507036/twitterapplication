package com.example.twitterapplication.mapper

import com.example.twitterapplication.dto.TwitterCommentRequest
import com.example.twitterapplication.model.TwitterComment

//done
fun TwitterCommentRequest.toTwitterComment(): TwitterComment {
    val entity = TwitterComment()
    entity.commentContent = this.commentContent
    return entity
}