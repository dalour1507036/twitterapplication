package com.example.twitterapplication.service.imp

import com.example.twitterapplication.dto.TwitterCommentRequest
import com.example.twitterapplication.dto.TwitterCommentResponse
import com.example.twitterapplication.mapper.toTwitterComment
import com.example.twitterapplication.mapper.toTwitterCommentResponse
import com.example.twitterapplication.model.TwitterComment
import com.example.twitterapplication.repository.TwitterCommentRepo
import com.example.twitterapplication.repository.TwitterPostRepo
import com.example.twitterapplication.repository.TwitterUserRepo
import com.example.twitterapplication.service.TwitterCommentService
import org.springframework.stereotype.Service

@Service
class TwitterCommentServiceImp(
    private val twitterUserRepo: TwitterUserRepo,
    private val twitterPostRepo: TwitterPostRepo,
    private val twitterCommentRepo: TwitterCommentRepo
    ) : TwitterCommentService {
    override fun createTwitterUserCommentInTwitterPost(
        twitterCommentRequest: TwitterCommentRequest,
        userId: Long,
        postId: Long
    ): TwitterCommentResponse {
        val twitterUser = twitterUserRepo.findById(userId).orElse(null)
        val twitterPost = twitterPostRepo.findById(postId).orElse(null)
        val twitterComment: TwitterComment = twitterCommentRequest.toTwitterComment()
        twitterComment.twitterUser = twitterUser
        twitterComment.twitterPost = twitterPost

        return twitterCommentRepo.save(twitterComment).toTwitterCommentResponse()

    }
}